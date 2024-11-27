package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NotchCompanion extends PlayerCompanion {
    public NotchCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public NotchCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Notch_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {

    }

}
