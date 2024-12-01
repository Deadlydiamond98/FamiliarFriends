package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CirnoCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OneUpMushroomCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.DamageTypeTags;
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

    @Shadow protected abstract boolean tryUseTotem(DamageSource source);

    @Shadow public abstract void onAttacking(Entity target);

    @Unique
    public LivingEntity getPlayer() {
        return ((LivingEntity)(Object)this);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "TAIL"), cancellable = true)
    private void onDeath(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                companion.onPlayerDeath(player);
                cir.setReturnValue(!player.isDead());
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
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
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion.canClimbWalls()) {
                    if (!getPlayer().isSpectator()) {
                        World world = player.getWorld();
                        BlockPos pos = getPlayer().getBlockPos();

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

    @Inject(method = "canWalkOnFluid", at = @At(value = "HEAD"), cancellable = true)
    private void walkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion instanceof CompanionCubeCompanion) {
                    if (!getPlayer().isSpectator()) {
                        cir.setReturnValue(state.isIn(FluidTags.LAVA) && !player.isInLava());
                    }
                }
            }
        }
    }

}
