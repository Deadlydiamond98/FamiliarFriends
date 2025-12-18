package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;

public class RanaCompanion extends PlayerCompanion {
    public RanaCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public RanaCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Rana_Companion, world, owner, gui);
    }

    @Override
    public void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 23, FamiliarFriendsConfig.Rana.jumpBoostLevel - 1, true, false));
    }

    @Override
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {
        return source.isIn(DamageTypeTags.IS_FALL) && FamiliarFriendsConfig.Rana.preventFallDamage;
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Rana.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Rana.enabled;
    }
}
