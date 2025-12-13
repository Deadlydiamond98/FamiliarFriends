package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.init.CompanionBlocks;
import net.deadlydiamond98.familiar_friends.common.commands.CompanionCommands;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.events.common.CompanionEvents;
import net.deadlydiamond98.familiar_friends.init.CompanionItems;
import net.deadlydiamond98.familiar_friends.networking.CompanionPackets;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.deadlydiamond98.koalalib.config.KoalaConfigCreator;
import net.deadlydiamond98.koalalib.updater.KoalaUpdateChecker;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FamiliarFriends implements ModInitializer {

	public static final String MOD_ID = "familiar_friends";
    public static final Logger LOGGER = LoggerFactory.getLogger("familiar_friends");

	@Override
	public void onInitialize() {
		KoalaConfigCreator.addModConfig(MOD_ID, FamiliarFriendsConfig.Main.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "oneup", FamiliarFriendsConfig.OneUp.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "allay", FamiliarFriendsConfig.Allay.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "cavespider", FamiliarFriendsConfig.CaveSpider.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "chicken", FamiliarFriendsConfig.Chicken.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "cirno", FamiliarFriendsConfig.Cirno.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "companionCube", FamiliarFriendsConfig.CompanionCube.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "creeper", FamiliarFriendsConfig.Creeper.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "goat", FamiliarFriendsConfig.Goat.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "herobrine", FamiliarFriendsConfig.Herobrine.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "ironGolem", FamiliarFriendsConfig.IronGolem.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "jeb", FamiliarFriendsConfig.Jeb.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "lemon", FamiliarFriendsConfig.Lemon.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "mrSaturn", FamiliarFriendsConfig.MrSaturn.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "navi", FamiliarFriendsConfig.Navi.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "ocelot", FamiliarFriendsConfig.Ocelot.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "rana", FamiliarFriendsConfig.Rana.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "skeleton", FamiliarFriendsConfig.Skeleton.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "snowGolem", FamiliarFriendsConfig.SnowGolem.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "spider", FamiliarFriendsConfig.Spider.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "squid", FamiliarFriendsConfig.Squid.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "tater", FamiliarFriendsConfig.Tater.class);
		KoalaConfigCreator.addModConfigCategory(MOD_ID, "vampire", FamiliarFriendsConfig.Vampire.class);

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