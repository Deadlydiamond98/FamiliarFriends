package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.*;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.*;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class CompanionEntityTypes {

    public static void registerEntities() {
    }

    public static final EntityType<RanaCompanion> Rana_Companion = CompanionRegistry.registerCompanion(
            RanaCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "rana_companion")
    );

    public static final EntityType<CirnoCompanion> Cirno_Companion = CompanionRegistry.registerCompanion(
            CirnoCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "cirno_companion")
    );

    public static final EntityType<GoatCompanion> Goat_Companion = CompanionRegistry.registerCompanion(
            GoatCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "goat_companion")
    );

    public static final EntityType<CaveSpiderCompanion> Cave_Spider_Companion = CompanionRegistry.registerCompanion(
            CaveSpiderCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "cave_spider_companion")
    );

    public static final EntityType<CreeperCompanion> Creeper_Companion = CompanionRegistry.registerCompanion(
            CreeperCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "creeper_companion")
    );

    public static final EntityType<AllayCompanion> Allay_Companion = CompanionRegistry.registerCompanion(
            AllayCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "allay_companion")
    );

    public static final EntityType<SnowGolemCompanion> Snow_Golem_Companion = CompanionRegistry.registerCompanion(
            SnowGolemCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "snow_golem_companion")
    );

    public static final EntityType<ChickenCompanion> Chicken_Companion = CompanionRegistry.registerCompanion(
            ChickenCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "chicken_companion")
    );

    public static final EntityType<IronGolemCompanion> Iron_Golem_Companion = CompanionRegistry.registerCompanion(
            IronGolemCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "iron_golem_companion")
    );

    public static final EntityType<NaviCompanion> Navi_Companion = CompanionRegistry.registerCompanion(
            NaviCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "navi_companion")
    );

    public static final EntityType<OcelotCompanion> Ocelot_Companion = CompanionRegistry.registerCompanion(
            OcelotCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "ocelot_companion")
    );

    public static final EntityType<SquidCompanion> Squid_Companion = CompanionRegistry.registerCompanion(
            SquidCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "squid_companion")
    );
    public static final EntityType<OneUpMushroomCompanion> One_Up_Mushroom_Companion = CompanionRegistry.registerCompanion(
            OneUpMushroomCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "one_up_mushroom_companion")
    );
    public static final EntityType<CompanionCubeCompanion> Companion_Cube_Companion = CompanionRegistry.registerCompanion(
            CompanionCubeCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "companion_cube_companion")
    );
    public static final EntityType<NotchCompanion> Notch_Companion = CompanionRegistry.registerCompanion(
            NotchCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "notch_companion")
    );
    public static final EntityType<HerobrineCompanion> Herobrine_Companion = CompanionRegistry.registerCompanion(
            HerobrineCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "herobrine_companion")
    );
    public static final EntityType<SpiderCompanion> Spider_Companion = CompanionRegistry.registerCompanion(
            SpiderCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "spider_companion")
    );
    public static final EntityType<SkeletonCompanion> Skeleton_Companion = CompanionRegistry.registerCompanion(
            SkeletonCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "skeleton_companion")
    );

    public static final EntityType<TinyTaterCompanion> Tiny_Tater_Companion = CompanionRegistry.registerCompanion(
            TinyTaterCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "tater_companion")
    );

    public static final EntityType<MrSaturnCompanion> Mr_Saturn_Companion = CompanionRegistry.registerCompanion(
            MrSaturnCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "mr_saturn_companion")
    );

}