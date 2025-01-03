package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.*;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.*;
import net.deadlydiamond98.familiar_friends.entities.projectiles.CirnoProjectile;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CompanionEntityTypes {

    public static void registerEntities() {
    }

    public static final EntityType<BiterCompanion> Biter_Companion = CompanionRegistry.registerCompanion(
            BiterCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "biter_companion")
    );

    public static final EntityType<SuperMushroomCompanion> Super_Mushroom_Companion = CompanionRegistry.registerCompanion(
            SuperMushroomCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "super_mushroom_companion")
    );

    public static final EntityType<MiniMushroomCompanion> Mini_Mushroom_Companion = CompanionRegistry.registerCompanion(
            MiniMushroomCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "mini_mushroom_companion")
    );

    public static final EntityType<RanaCompanion> Rana_Companion = CompanionRegistry.registerCompanion(
            RanaCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "rana_companion")
    );

    public static final EntityType<LemonCompanion> Lemon_Companion = CompanionRegistry.registerCompanion(
            LemonCompanion.class,
            Identifier.of(FamiliarFriends.MOD_ID, "lemon_companion")
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


    //Other Non-Companion Entities

    public static final EntityType<CirnoProjectile> Cirno_Projectile = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "cirno_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CirnoProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.4f,0.2f))
                    .build()
    );

}
