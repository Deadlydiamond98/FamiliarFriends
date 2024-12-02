package net.deadlydiamond98.familiar_friends.items;

import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BookOfFamiliars extends Item {

    public BookOfFamiliars(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            CompanionServerPackets.openCompanionBookScreen((ServerPlayerEntity) user);
        }

        return super.use(world, user, hand);
    }
}
