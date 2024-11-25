package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.IronGolemCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
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

    public static final EntityType<CreeperCompanion> Creeper_Companion = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "creeper_companion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<CreeperCompanion> type, World world) ->
                            new CreeperCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );

    public static final EntityType<ChickenCompanion> Chicken_Companion = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "creeper_companion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<ChickenCompanion> type, World world) ->
                            new ChickenCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );

    public static final EntityType<IronGolemCompanion> Iron_Golem_Companion = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "iron_golem_companion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<IronGolemCompanion> type, World world) ->
                            new IronGolemCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );

    public static final EntityType<NaviCompanion> Navi_Companion = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "navi_companion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<NaviCompanion> type, World world) ->
                            new NaviCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );
}
