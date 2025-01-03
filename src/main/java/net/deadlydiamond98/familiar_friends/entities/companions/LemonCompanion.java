package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.projectiles.CirnoProjectile;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.block.FluidBlock.LEVEL;

public class LemonCompanion extends PlayerCompanion {
    public LemonCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public LemonCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Lemon_Companion, world, owner, gui);
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        if (this.hasNoCooldown(player)) {
            Box boundingBox = new Box(
                    player.getX() - 2, player.getY() - 2, player.getZ() - 2,
                    player.getX() + 2, player.getY() + 2, player.getZ() + 2
            );

            if (player.getWorld() instanceof ServerWorld serverWorld) {

                serverWorld.spawnParticles(
                        ParticleTypes.FALLING_HONEY,
                        player.getX(), player.getY(), player.getZ(),
                        50,
                        1.5, 1.5, 1.5,
                        0.1
                );

                serverWorld.getEntitiesByClass(
                        LivingEntity.class,
                        boundingBox,
                        entity -> entity != player && entity.isAttackable()
                ).forEach(entity -> {
                    entity.damage(player.getDamageSources().magic(), 5.0f);
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1));
                });
            }

            this.setCooldownSeconds(15);
        }
    }

    @Override
    public int getCost() {
        return CompanionConfig.lemonCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.lemonEnabled;
    }
}
