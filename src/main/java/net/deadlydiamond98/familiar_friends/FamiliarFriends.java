package net.deadlydiamond98.familiar_friends;

import eu.midnightdust.lib.config.MidnightConfig;
import net.deadlydiamond98.familiar_friends.commands.CompanionCommands;
import net.deadlydiamond98.familiar_friends.effects.CompanionEffects;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.events.CompanionEvents;
import net.deadlydiamond98.familiar_friends.items.CompanionItems;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamiliarFriends implements ModInitializer {

	public static final String MOD_ID = "familiar_friends";
    public static final Logger LOGGER = LoggerFactory.getLogger("familiar_friends");

	public static String Current_Keybinding_Key = "R";

	@Override
	public void onInitialize() {

		MidnightConfig.init(MOD_ID, CompanionConfig.class);

		CompanionSounds.registerSounds();

		CompanionServerPackets.registerServerPackets();

		CompanionItems.registerItems();
		CompanionEntityTypes.registerEntities();

		CompanionEvents.registerEvents();
		CompanionCommands.register();

		CompanionEffects.registerEffects();
		LOGGER.info(MOD_ID + " has loaded successfully");
	}
}