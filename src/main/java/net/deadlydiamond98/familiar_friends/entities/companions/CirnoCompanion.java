package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CirnoCompanion extends PlayerCompanion {
    public CirnoCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CirnoCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Cirno_Companion, world, owner, gui);
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
    public int getCost() {
        return 9;
    }
}
