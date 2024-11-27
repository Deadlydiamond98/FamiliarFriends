package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.deadlydiamond98.familiar_friends.util.BookCompanionRegistry;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements CompanionPlayerData {

    @Unique
    private boolean hasCompanion;

    @Unique
    private List<String> unlockedCompanions;

    @Nullable
    @Unique
    private PlayerCompanion currentCompanion;

    @Unique
    private String backUpCompanionKey;

    public PlayerEntity getPlayer() {
        return ((PlayerEntity)(Object)this);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.hasCompanion = false;
        this.unlockedCompanions = new ArrayList<>();
        this.currentCompanion = null;
        this.backUpCompanionKey = "";
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!getPlayer().getWorld().isClient()) {
            CompanionServerPackets.syncCompanionPlayerData((ServerPlayerEntity) getPlayer(), this.unlockedCompanions, this.backUpCompanionKey);

            if (this.hasCompanion && isMyFriendDead()) {

                this.currentCompanion = createCompanion();

                if (this.currentCompanion != null) {
                    this.getPlayer().getWorld().spawnEntity(this.currentCompanion);
                }

            }
            else if (!this.hasCompanion && !isMyFriendDead()) {

                if (this.currentCompanion != null) {
                    this.currentCompanion.setRemoved(Entity.RemovalReason.DISCARDED);
                }

                this.currentCompanion = null;
            }
        }
    }

    @Unique
    private boolean isMyFriendDead() {
        if (this.currentCompanion == null) {
            return true;
        }

        return this.currentCompanion.isRemoved();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("hasCompanion", this.hasCompanion);
        nbt.putString("backUpCompanionKey", this.backUpCompanionKey);

        NbtList companionList = new NbtList();
        for (String companion : this.unlockedCompanions) {
            companionList.add(NbtString.of(companion));
        }
        nbt.put("unlockedCompanions", companionList);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("hasCompanion")) {
            this.hasCompanion = nbt.getBoolean("hasCompanion");
        }
        if (nbt.contains("backUpCompanionKey")) {
            this.backUpCompanionKey = nbt.getString("backUpCompanionKey");
        }

        if (nbt.contains("unlockedCompanions", 9)) {
            NbtList companionList = nbt.getList("unlockedCompanions", 8);
            this.unlockedCompanions.clear();
            for (int i = 0; i < companionList.size(); i++) {
                this.unlockedCompanions.add(companionList.getString(i));
            }
        }
    }

    @Override
    public boolean hasCompanion() {
        return this.hasCompanion;
    }

    @Override
    public void setCompanionStatus(boolean hasCompanion) {
        this.hasCompanion = hasCompanion;
    }

    @Override
    public boolean isCompanionUnlocked(PlayerCompanion companion) {
        return this.unlockedCompanions.contains(companion.getType().getTranslationKey());
    }

    @Override
    public void unlockCompanion(String companion) {
        this.unlockedCompanions.add(companion);
    }

    @Override
    public void syncUnlockedList(List<String> unlockedCompanions) {
        this.unlockedCompanions = unlockedCompanions;
    }

    @Override
    public void removeAllCompanions() {
        this.unlockedCompanions.clear();

        if (this.currentCompanion != null) {
            this.currentCompanion.setRemoved(Entity.RemovalReason.DISCARDED);
        }

        this.backUpCompanionKey = "";
        this.hasCompanion = false;
    }

    @Override
    public void revokeCompanion(String companion) {
        this.unlockedCompanions.remove(companion);

        if (this.currentCompanion.getType().getTranslationKey().equals(companion)) {
            this.hasCompanion = false;
            this.currentCompanion.remove(Entity.RemovalReason.DISCARDED);
            this.backUpCompanionKey = "";
        }
    }

    @Override
    public void grantCompanion(PlayerCompanion companion) {

        if (this.currentCompanion != null && !this.currentCompanion.isRemoved()) {
            this.currentCompanion.setRemoved(Entity.RemovalReason.DISCARDED);
        }

        this.currentCompanion = companion;
        this.backUpCompanionKey = companion.getType().getTranslationKey();
        this.hasCompanion = true;
    }

    @Nullable
    @Override
    public String currentCompanion() {
        return this.backUpCompanionKey;
    }

    @Override
    public void syncCurrentCompanion(String companion) {
        this.backUpCompanionKey = companion;
    }

    @Override
    public void doCompanionKeybind() {
        if (this.currentCompanion != null) {
            this.currentCompanion.doKeyEvent(getPlayer());
        }
    }

    @Override
    public PlayerCompanion getCompanion() {
        return createCompanion();
    }

    @Unique
    private PlayerCompanion createCompanion() {
        for (Class<? extends PlayerCompanion> companionClass : BookCompanionRegistry.COMPANIONS) {
            try {

                PlayerCompanion companionInstance = companionClass.getConstructor(
                        World.class,
                        PlayerEntity.class,
                        boolean.class
                ).newInstance(
                        getPlayer().getWorld(),
                        getPlayer(),
                        false
                );

                if (companionInstance.getType().getTranslationKey().equals(this.backUpCompanionKey)) {

                    return companionInstance;

                }

            } catch (Exception ignore) {

            }
        }
        return null;
    }
}