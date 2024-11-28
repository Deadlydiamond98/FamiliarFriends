package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class OneUpMushroomCompanion extends PlayerCompanion {
    public OneUpMushroomCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public OneUpMushroomCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.One_Up_Mushroom_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public int getCost() {
        return 0;
    }
}
