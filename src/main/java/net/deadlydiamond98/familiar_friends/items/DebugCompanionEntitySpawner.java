package net.deadlydiamond98.familiar_friends.items;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OcelotCompanion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugCompanionEntitySpawner extends Item {
    public DebugCompanionEntitySpawner(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        PlayerCompanion testEntity = new OcelotCompanion(world, user, false);

        world.spawnEntity(testEntity);

        return super.use(world, user, hand);
    }
}
