package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OneUpMushroomCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow private Optional<BlockPos> climbingPos;

    @Unique
    public LivingEntity getPlayer() {
        return ((LivingEntity)(Object)this);
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void onDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion instanceof OneUpMushroomCompanion) {
                    if (player.getHealth() - amount <= 0.0f) {
                        player.setHealth(player.getMaxHealth());
                        companion.playSound(CompanionSounds.One_Up, 1.0f, 1.0f);
                        player.lockCompanion(companion.getType().getTranslationKey());
                    }
                }
                if (companion instanceof CreeperCompanion) {
                    if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

    @Inject(method = "onDeath", at = @At(value = "HEAD"), cancellable = true)
    private void died(DamageSource damageSource, CallbackInfo ci) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion instanceof CreeperCompanion) {
                    player.lockCompanion(companion.getType().getTranslationKey());
                    player.getWorld().createExplosion(player, player.getX(), player.getY(), player.getZ(),
                            4, World.ExplosionSourceType.NONE);
                }
            }
        }
    }

    @Inject(method = "isClimbing", at = @At(value = "HEAD"), cancellable = true)
    private void climb(CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion instanceof SpiderCompanion) {
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
