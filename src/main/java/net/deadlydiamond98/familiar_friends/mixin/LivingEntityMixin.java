package net.deadlydiamond98.familiar_friends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow private Optional<BlockPos> climbingPos;

    @Unique
    public LivingEntity familiar_friends$getLiving() {
        return ((LivingEntity)(Object)this);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "TAIL"), cancellable = true)
    private void onDeath(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this.familiar_friends$getLiving() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                companion.onPlayerDeath(player);
                cir.setReturnValue(!player.isDead());
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.familiar_friends$getLiving() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion.onDamaged(source, amount, player)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "isClimbing", at = @At(value = "HEAD"), cancellable = true)
    private void climb(CallbackInfoReturnable<Boolean> cir) {
        if (this.familiar_friends$getLiving() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion.canClimbWalls()) {
                    if (!familiar_friends$getLiving().isSpectator()) {
                        World world = player.getWorld();
                        BlockPos pos = familiar_friends$getLiving().getBlockPos();

                        boolean isNearClimbable = world.getBlockState(pos.north()).isSolidBlock(world, pos.north())
                                || world.getBlockState(pos.south()).isSolidBlock(world, pos.south())
                                || world.getBlockState(pos.east()).isSolidBlock(world, pos.east())
                                || world.getBlockState(pos.west()).isSolidBlock(world, pos.west());

                        if (isNearClimbable) {
                            this.climbingPos = Optional.of(pos);
                            cir.setReturnValue(true);
                        }
                    }
                }
            }
        }
    }

    @ModifyReturnValue(method = "canWalkOnFluid", at = @At(value = "RETURN"))
    private boolean familiar_friends$canWalkOnFluid(boolean original, @Local FluidState state) {
        if (this.familiar_friends$getLiving() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (!familiar_friends$getLiving().isSpectator()) {
                    if (companion.walkableFluids() != null) {
                        return state.isIn(companion.walkableFluids()) && !player.isInLava() && !player.isTouchingWater();
                    }
                }
            }
        }
        return original;
    }
}
