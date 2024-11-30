package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class TinyTaterCompanion extends PlayerCompanion {

    public TinyTaterCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Tiny_Tater_Companion, world, owner, gui);
    }

    public TinyTaterCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (player.isDead()) {
            this.playSound(CompanionSounds.DoIt, 0.5f, 1.0f);
        }
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        this.playSound(CompanionSounds.DoIt, 0.5f, 1.0f);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        this.playSound(CompanionSounds.DoIt, 0.5f, 1.0f);
    }

    @Override
    public int getCost() {
        return 0;
    }
}