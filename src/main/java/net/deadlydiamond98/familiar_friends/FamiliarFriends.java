package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.items.CompanionItems;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.deadlydiamond98.familiar_friends.screens.CompanionScreenHandlers;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.BookCompanionRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamiliarFriends implements ModInitializer {

	public static final String MOD_ID = "familiar_friends";
    public static final Logger LOGGER = LoggerFactory.getLogger("familiar_friends");

	public static String Current_Keybinding_Key = "R";

	/**
	 * Example of adding a Companion to a book in your own mod:
	 *		BookCompanionRegistry.addCompanion(**Insert Companion Class Here**.class);
	 */

	@Override
	public void onInitialize() {

		// Add the initial classes that come with the mod!
		BookCompanionRegistry.addCompanions();

		CompanionSounds.registerSounds();

		CompanionServerPackets.registerServerPackets();

		CompanionItems.registerItems();
		CompanionEntities.registerEntities();
		CompanionScreenHandlers.registerScreenHandlers();
		LOGGER.info(MOD_ID + " has loaded successfully");
	}
}