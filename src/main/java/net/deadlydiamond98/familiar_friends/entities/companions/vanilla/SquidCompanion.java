package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SquidCompanion extends PlayerCompanion {
    public SquidCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SquidCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Squid_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (!player.isSubmergedInWater()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, true, false));
        }
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {

    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.squid");
    }

    @Override
    public int getCost() {
        return 12;
    }
}
