package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SnowGolemCompanion extends PlayerCompanion {

    public SnowGolemCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SnowGolemCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Snow_Golem_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.snow_golem");
    }

    @Override
    public int getCost() {
        return 0;
    }
}
