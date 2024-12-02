package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ChickenCompanion extends PlayerCompanion {

    public ChickenCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public ChickenCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Chicken_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (player.isOnGround()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 43, 0, true, false));
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.chicken");
    }

    @Override
    public int getCost() {
        return CompanionConfig.chickenCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.chickenEnabled;
    }
}
