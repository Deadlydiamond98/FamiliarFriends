package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

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
                    entity.damage(player.getDamageSources().magic(), (float) FamiliarFriendsConfig.Lemon.damage);
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, FamiliarFriendsConfig.Lemon.slownessDuration, 1));
                });
            }

            this.setCooldownSeconds(FamiliarFriendsConfig.Lemon.abilityCooldown);
        }
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Lemon.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Lemon.enabled;
    }
}
