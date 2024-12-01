package net.deadlydiamond98.familiar_friends.events;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.mixin.LivingEntityAccessor;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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
            float baseDamage = player.isUsingRiptide()
                    ? ((LivingEntityAccessor) player).getRiptideAttackDamage()
                    : (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            float cooldownProgress = player.getAttackCooldownProgress(0.5F);
            float scaledDamage = baseDamage * (0.2F + cooldownProgress * cooldownProgress * 0.8F);

            ItemStack weapon = player.getWeaponStack();
            DamageSource damageSource = player.getDamageSources().playerAttack(player);
            float bonusDamage = weapon.getItem().getBonusAttackDamage(target, scaledDamage, damageSource);
            float totalDamage = scaledDamage + bonusDamage;

            boolean isCritical = cooldownProgress > 0.9F
                    && player.fallDistance > 0.0F
                    && !player.isOnGround()
                    && !player.isClimbing()
                    && !player.isTouchingWater()
                    && !player.hasStatusEffect(StatusEffects.BLINDNESS)
                    && !player.hasVehicle()
                    && target instanceof LivingEntity
                    && !player.isSprinting();

            if (isCritical) {
                totalDamage *= 1.5F;
            }

            if (target instanceof LivingEntity livingTarget) {
                totalDamage = EnchantmentHelper.getDamage(
                        player.getWorld().getServer().getWorld(player.getWorld().getRegistryKey()),
                        weapon,
                        livingTarget,
                        damageSource,
                        totalDamage
                );
            }

            return totalDamage;
        }
        return 0;
    }
}
