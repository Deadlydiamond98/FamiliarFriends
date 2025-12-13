package net.deadlydiamond98.familiar_friends.common.items;

import net.deadlydiamond98.familiar_friends.networking.s2c.OpenCompanionBookS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BookOfFamiliars extends Item {

    public BookOfFamiliars(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            OpenCompanionBookS2CPacket.send(serverPlayer);
        }
        return super.use(world, user, hand);
    }
}
