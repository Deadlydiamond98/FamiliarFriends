package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionEntityTypes;
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
        return FamiliarFriendsConfig.CompanionCube.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.CompanionCube.enabled;
    }
}
