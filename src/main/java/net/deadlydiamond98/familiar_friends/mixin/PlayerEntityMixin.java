package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Unique
    private boolean hasCompanion;

    @Unique
    @Nullable
    private PlayerCompanion companion;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.hasCompanion = false;
        this.companion = null;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {

        if (this.companion != null) {
            if (this.hasCompanion && this.companion.isRemoved()) {
                this.companion = createNewCompanion();
            }
            else if (!this.hasCompanion && this.companion.isRemoved()) {
                this.companion = null;
            }
        }
    }

    @Unique
    private PlayerCompanion createNewCompanion() {

        if (this.companion == null) {
            return null;
        }

        PlayerCompanion newCompanion;

        try {
            Class<? extends PlayerCompanion> companionClass = this.companion.getClass();

            newCompanion = companionClass.getDeclaredConstructor().newInstance();

            return newCompanion;
        } catch (Exception e) {
            return null;
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void onSave(NbtCompound nbt, CallbackInfo info) {
        nbt.putBoolean("hasCompanion", hasCompanion);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void onLoad(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("hasCompanion")) {
            this.hasCompanion = nbt.getBoolean("hasCompanion");
        }
    }
}