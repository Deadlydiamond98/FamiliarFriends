package net.deadlydiamond98.familiar_friends.events;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class CompanionEvents {

    public static void registerEvents() {
        ServerPlayerEvents.AFTER_RESPAWN.register(new OnPlayerDeathEvent());
        onAttackEvent();
    }

    public static void onAttackEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            PlayerCompanion currentCompanion = player.getCompanion();

            if (currentCompanion != null && entity instanceof LivingEntity livingEntity && !player.isSpectator() && !world.isClient()) {
                currentCompanion.onAttack(player, livingEntity, calculateAttackDamage(player, livingEntity));
            }
            return ActionResult.PASS;
        });
    }

    public static float calculateAttackDamage(PlayerEntity player, Entity target) {
        if (!player.getWorld().isClient()) {
            if (!target.isAttackable() || target.handleAttack(player)) {
                return 0;
            }

            float baseDamage = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            float enchantmentDamage;
            if (target instanceof LivingEntity) {
                enchantmentDamage = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), ((LivingEntity) target).getGroup());
            } else {
                enchantmentDamage = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), EntityGroup.DEFAULT);
            }

            float cooldownProgress = player.getAttackCooldownProgress(0.5f);
            baseDamage *= 0.2f + cooldownProgress * cooldownProgress * 0.8f;
            enchantmentDamage *= cooldownProgress;

            boolean isCriticalHit = cooldownProgress > 0.9f &&
                    player.fallDistance > 0 &&
                    !player.isOnGround() &&
                    !player.isClimbing() &&
                    !player.isTouchingWater() &&
                    !player.hasStatusEffect(StatusEffects.BLINDNESS) &&
                    !player.hasVehicle() &&
                    target instanceof LivingEntity &&
                    !player.isSprinting();

            if (isCriticalHit) {
                baseDamage *= 1.5f;
            }

            return baseDamage + enchantmentDamage;
        }
        return 0;
    }
}
