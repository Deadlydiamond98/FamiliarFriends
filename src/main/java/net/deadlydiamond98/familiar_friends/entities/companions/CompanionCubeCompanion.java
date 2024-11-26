package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CompanionCubeCompanion extends PlayerCompanion {
    public CompanionCubeCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CompanionCubeCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Companion_Cube_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {
    }
}
