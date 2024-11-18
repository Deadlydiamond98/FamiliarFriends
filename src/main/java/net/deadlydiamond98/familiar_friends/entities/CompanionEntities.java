package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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

    public static final EntityType<PlayerCompanion> Player_Companion = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FamiliarFriends.MOD_ID, "player_companion"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType<PlayerCompanion> type, World world) ->
                            new PlayerCompanion(type, world))
                    .dimensions(EntityDimensions.fixed(0.4f,0.4f))
                    .disableSummon()
                    .build()
    );
}
