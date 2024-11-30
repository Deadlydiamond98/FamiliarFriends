package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class TinyTater extends PlayerCompanion {

    public TinyTater(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Tiny_Tater, world, owner, gui);
    }

    public TinyTater(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (player.isDead()) {
            this.playSound(CompanionSounds.DO_IT, 1.0f, 1);
        }
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        this.playSound(CompanionSounds.DO_IT, 1.0f, 1);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        this.playSound(CompanionSounds.DO_IT, 1.0f, 1);
    }
}
