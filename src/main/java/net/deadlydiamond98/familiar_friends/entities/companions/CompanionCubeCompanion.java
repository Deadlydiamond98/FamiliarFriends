package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CompanionCubeCompanion extends PlayerCompanion {
    public CompanionCubeCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CompanionCubeCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Companion_Cube_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public int getCost() {
        return CompanionConfig.companionCubeCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.companionCubeEnabled;
    }
}
