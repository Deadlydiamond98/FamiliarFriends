package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.init.CompanionBlocks;
import net.deadlydiamond98.familiar_friends.common.commands.CompanionCommands;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.events.common.CompanionEvents;
import net.deadlydiamond98.familiar_friends.init.CompanionConfigs;
import net.deadlydiamond98.familiar_friends.init.CompanionItems;
import net.deadlydiamond98.familiar_friends.networking.CompanionPackets;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.deadlydiamond98.koalalib.updater.KoalaUpdateChecker;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamiliarFriends implements ModInitializer {

	public static final String MOD_ID = "familiar_friends";
    public static final Logger LOGGER = LoggerFactory.getLogger("familiar_friends");

	@Override
	public void onInitialize() {
		CompanionConfigs.register();
		KoalaUpdateChecker.addModUpdateChecker(MOD_ID);

		CompanionSounds.registerSounds();
		CompanionPackets.registerC2SPackets();

		CompanionItems.registerItems();
		CompanionBlocks.registerBlocks();
		CompanionEntityTypes.register();

		CompanionEvents.registerEvents();
		CompanionCommands.register();

		LOGGER.info(MOD_ID + " has loaded successfully");
	}
}