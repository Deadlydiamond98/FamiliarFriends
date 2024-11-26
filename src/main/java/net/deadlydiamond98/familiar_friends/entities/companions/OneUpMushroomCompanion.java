package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class OneUpMushroomCompanion extends PlayerCompanion {
    public OneUpMushroomCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public OneUpMushroomCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.One_Up_Mushroom_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {
    }
}
