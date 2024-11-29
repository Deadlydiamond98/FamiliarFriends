package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;
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
    private List<String> unlockedCompanions;
    @Unique
    private List<String> lastSyncedUnlockedCompanions;
    @Nullable
    @Unique
    private PlayerCompanion currentCompanion;
    @Unique
    private String backUpCompanionKey;
    @Unique
    private String lastSyncedBackUpCompanionKey;
    @Unique
    private boolean hasCompanion;

    public PlayerEntity getPlayer() {
        return ((PlayerEntity)(Object)this);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.hasCompanion = false;
        this.unlockedCompanions = new ArrayList<>();
        this.currentCompanion = null;
        this.backUpCompanionKey = "";

        String highlyImprobableString = "42069, Trying to make a string that will never happen so this updates when the " +
                "player's joins, and so that this isn't mistaken for an entity";

        this.lastSyncedBackUpCompanionKey = highlyImprobableString;
        this.lastSyncedUnlockedCompanions = new ArrayList<>();
        this.lastSyncedUnlockedCompanions.add(highlyImprobableString);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!getPlayer().getWorld().isClient()) {
            SyncCompanionData();
            updateCompanionExistance();
        }
    }


    // Only sync when data doesn't match
    @Unique
    private void SyncCompanionData() {
        if (!this.lastSyncedBackUpCompanionKey.equals(this.backUpCompanionKey) || !this.lastSyncedUnlockedCompanions.equals(this.unlockedCompanions)) {
            CompanionServerPackets.syncCompanionPlayerData((ServerPlayerEntity) getPlayer(), this.unlockedCompanions, this.backUpCompanionKey);
        }
    }

    // these are just making sure the Companion doesn't disappear if the Companion gets too far or Worlds
    // are changed or something, and then also has removal if the companion should no longer exist
    private void updateCompanionExistance() {
        if (this.hasCompanion && isMyFriendDead()) {

            this.currentCompanion = this.getCompanion();
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

    // Check if your companion is still with you
    @Unique
    private boolean isMyFriendDead() {
        if (this.currentCompanion == null) {
            return true;
        }

        return this.currentCompanion.isRemoved();
    }

    // Checks if a companion is unlocked
    @Override
    public boolean isCompanionUnlocked(PlayerCompanion companion) {
        return this.unlockedCompanions.contains(companion.getType().getTranslationKey());
    }

    // Adds companion to the list of companions, removes the lock in the gui and enables the equip button
    @Override
    public void unlockCompanion(String companion) {
        this.unlockedCompanions.add(companion);
    }

    // Ran on the client, syncs the unlocked companions since they don't save to the client properly
    @Override
    public void syncUnlockedList(List<String> unlockedCompanions) {
        this.unlockedCompanions = unlockedCompanions;
    }

    // Removes all Companions from the player, is a leftover from debugging, but I'm keeping it just in case
    @Override
    public void lockAllCompanions() {
        this.unlockedCompanions.clear();

        if (this.currentCompanion != null) {
            this.currentCompanion.setRemoved(Entity.RemovalReason.DISCARDED);
        }

        this.backUpCompanionKey = "";
        this.hasCompanion = false;
    }

    // Re-lock a companion
    @Override
    public void lockCompanion(String companion) {
        this.unlockedCompanions.remove(companion);

        if (this.currentCompanion.getType().getTranslationKey().equals(companion)) {
            this.hasCompanion = false;
            this.currentCompanion.remove(Entity.RemovalReason.DISCARDED);
            this.currentCompanion = null;
            this.backUpCompanionKey = "";
        }
    }

    // Sets the player's current Companion
    @Override
    public void equipCompanion(PlayerCompanion companion) {

        if (this.currentCompanion != null && !this.currentCompanion.isRemoved()) {
            this.currentCompanion.setRemoved(Entity.RemovalReason.DISCARDED);
        }

        this.currentCompanion = companion;
        this.backUpCompanionKey = companion.getType().getTranslationKey();
        this.hasCompanion = true;
        getPlayer().getWorld().spawnEntity(companion);
    }

    @Override
    public void unequipCompanion(PlayerCompanion companion) {
        this.hasCompanion = false;
        this.currentCompanion.remove(Entity.RemovalReason.DISCARDED);
        this.currentCompanion = null;
        this.backUpCompanionKey = "";
    }

    // Gets the current Companion
    @Nullable
    @Override
    public String currentCompanion() {
        return this.backUpCompanionKey;
    }

    // Reminds the client that you have this companion so that you can't equip it in the gui
    @Override
    public void syncCurrentCompanion(String companion) {
        this.backUpCompanionKey = companion;
    }

    // does a companions "R" action event (or whatever keybind R may be)
    @Override
    public void doCompanionKeybind() {
        if (this.currentCompanion != null) {
            this.currentCompanion.doKeyEvent(getPlayer());
        }
    }

    // Gets the companion on the player as a Player Companion
    @Override
    public PlayerCompanion getCompanion() {
        return CompanionRegistry.createCompanion(this.backUpCompanionKey, getPlayer(), false);
    }

    // Past Here just NBT stuffs

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

    @Inject(method = "attack", at = @At("HEAD"))
    public void onAttackEntity(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingEntity) {
            if (this.currentCompanion != null) {
                this.currentCompanion.onAttack(this.getPlayer(), livingEntity);
            }
        }
    }
}