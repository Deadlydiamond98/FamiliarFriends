package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
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

    //IDK if a list like this is the best approach, but it works
    @Unique
    private List<String> unlockedCompanions;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.hasCompanion = false;
        this.unlockedCompanions = new ArrayList<>();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("hasCompanion", this.hasCompanion);

        NbtList companionList = new NbtList();
        for (String name : this.unlockedCompanions) {
            companionList.add(NbtString.of(name));
        }
        nbt.put("unlockedCompanions", companionList);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("hasCompanion")) {
            this.hasCompanion = nbt.getBoolean("hasCompanion");
        }

        if (nbt.contains("unlockedCompanions")) {
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
    public void unlockCompanion(PlayerCompanion companion) {
        this.unlockedCompanions.add(companion.getType().getTranslationKey());
    }
}