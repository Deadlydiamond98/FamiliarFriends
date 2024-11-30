package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MrSaturnCompanion extends PlayerCompanion {
    public MrSaturnCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public MrSaturnCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Companion_Cube_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {

//        int random = player.getRandom().nextInt(5);
//
//        if (random == 0) {
//            ItemStack mainHandItem = target.getMainHandStack();
//            ItemStack offHandItem = target.getOffHandStack();
//
//            target.getStackInHand(target.getActiveHand()).damage();
//        }
    }

    @Override
    public int getCost() {
        return 20;
    }
}
