package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CompanionEntities {
    public static void registerEntities() {
    }

    public static final EntityType<CreeperCompanion> Creeper_Companion = registerCompanion(
            "creeper_companion",
            CreeperCompanion::new
    );

    public static final EntityType<ChickenCompanion> Chicken_Companion = registerCompanion(
            "chicken_companion",
            ChickenCompanion::new
    );

    public static final EntityType<IronGolemCompanion> Iron_Golem_Companion = registerCompanion(
            "iron_golem_companion",
            IronGolemCompanion::new
    );

    public static final EntityType<NaviCompanion> Navi_Companion = registerCompanion(
            "navi_companion",
            NaviCompanion::new
    );

    public static final EntityType<OcelotCompanion> Ocelot_Companion = registerCompanion(
            "ocelot_companion",
            OcelotCompanion::new
    );

    public static final EntityType<SquidCompanion> Squid_Companion = registerCompanion(
            "squid_companion",
            SquidCompanion::new
    );
    public static final EntityType<OneUpMushroomCompanion> One_Up_Mushroom_Companion = registerCompanion(
            "one_up_mushroom_companion",
            OneUpMushroomCompanion::new
    );
    public static final EntityType<CompanionCubeCompanion> Companion_Cube_Companion = registerCompanion(
            "companion_cube_companion",
            CompanionCubeCompanion::new
    );


    public static <T extends Entity> EntityType<T> registerCompanion(
            String name,
            EntityType.EntityFactory<T> factory) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(FamiliarFriends.MOD_ID, name),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                        .dimensions(EntityDimensions.fixed(0.4f, 0.4f))
                        .disableSummon()
                        .build()
        );
    }
}
