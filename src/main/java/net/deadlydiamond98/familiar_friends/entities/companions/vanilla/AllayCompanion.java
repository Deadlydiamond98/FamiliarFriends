package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AllayCompanion extends PlayerCompanion {
    public AllayCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public AllayCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Allay_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        World world = player.getWorld();
        Vec3d playerPos = player.getPos();
        double radius = 4.0;

        List<ItemEntity> nearbyItems = world.getEntitiesByClass(ItemEntity.class,
                player.getBoundingBox().expand(radius),
                item -> true);

        for (ItemEntity item : nearbyItems) {
            if (!item.cannotPickup()) {
                item.setPosition(playerPos);
            }
        }
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {

    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.allay");
    }

    @Override
    public int getCost() {
        return 10;
    }
}
