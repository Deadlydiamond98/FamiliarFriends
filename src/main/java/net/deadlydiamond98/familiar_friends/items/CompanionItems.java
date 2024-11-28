package net.deadlydiamond98.familiar_friends.items;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class CompanionItems {

    public static final Item Book_Of_Familiars = registerItem("book_of_familiars", new BookOfFamiliars(new Item.Settings()
            .rarity(Rarity.UNCOMMON)));

    //Registration stuff
    private static Item registerItem(String itemName, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(FamiliarFriends.MOD_ID, itemName), item);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(Book_Of_Familiars));
        FamiliarFriends.LOGGER.debug("Registering Items for" + FamiliarFriends.MOD_ID);
    }
}
