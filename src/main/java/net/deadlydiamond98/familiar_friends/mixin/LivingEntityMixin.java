package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OneUpMushroomCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    public LivingEntity getPlayer() {
        return ((LivingEntity)(Object)this);
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void beforeTryUseTotem(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (((CompanionPlayerData) player).getCompanion() != null) {
                PlayerCompanion companion = ((CompanionPlayerData) player).getCompanion();
                if (companion instanceof OneUpMushroomCompanion) {
                    if (player.getHealth() - amount <= 0.0f) {
                        player.setHealth(player.getMaxHealth());
                        companion.playSound(CompanionSounds.OneUp, 1.0f, 1.0f);
                        ((CompanionPlayerData) player).revokeCompanion(companion.getType().getTranslationKey());
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

    @Inject(method = "canWalkOnFluid", at = @At(value = "RETURN"), cancellable = true)
    private void walkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {

        if (this.getPlayer() instanceof PlayerEntity player) {
            if (((CompanionPlayerData) player).getCompanion() != null) {
                PlayerCompanion companion = ((CompanionPlayerData) player).getCompanion();
                if (state.isOf(Fluids.LAVA) || state.isOf(Fluids.FLOWING_LAVA) && companion instanceof CompanionCubeCompanion) {
                    cir.setReturnValue(true);
                    cir.cancel();
                }
            }
        }
    }

}
