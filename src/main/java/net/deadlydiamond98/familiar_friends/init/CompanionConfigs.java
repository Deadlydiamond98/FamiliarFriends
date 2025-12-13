package net.deadlydiamond98.familiar_friends.init;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.koalalib.config.KoalaConfigCreator;

public class CompanionConfigs {
    public static void register() {
        KoalaConfigCreator.addModConfig(FamiliarFriends.MOD_ID, FamiliarFriendsConfig.Main.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "oneup", FamiliarFriendsConfig.OneUp.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "allay", FamiliarFriendsConfig.Allay.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "cavespider", FamiliarFriendsConfig.CaveSpider.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "chicken", FamiliarFriendsConfig.Chicken.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "cirno", FamiliarFriendsConfig.Cirno.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "companionCube", FamiliarFriendsConfig.CompanionCube.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "creeper", FamiliarFriendsConfig.Creeper.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "goat", FamiliarFriendsConfig.Goat.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "herobrine", FamiliarFriendsConfig.Herobrine.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "ironGolem", FamiliarFriendsConfig.IronGolem.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "jeb", FamiliarFriendsConfig.Jeb.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "lemon", FamiliarFriendsConfig.Lemon.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "mrSaturn", FamiliarFriendsConfig.MrSaturn.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "navi", FamiliarFriendsConfig.Navi.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "ocelot", FamiliarFriendsConfig.Ocelot.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "rana", FamiliarFriendsConfig.Rana.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "skeleton", FamiliarFriendsConfig.Skeleton.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "snowGolem", FamiliarFriendsConfig.SnowGolem.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "spider", FamiliarFriendsConfig.Spider.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "squid", FamiliarFriendsConfig.Squid.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "tater", FamiliarFriendsConfig.Tater.class);
        KoalaConfigCreator.addModConfigCategory(FamiliarFriends.MOD_ID, "vampire", FamiliarFriendsConfig.Vampire.class);
    }
}
