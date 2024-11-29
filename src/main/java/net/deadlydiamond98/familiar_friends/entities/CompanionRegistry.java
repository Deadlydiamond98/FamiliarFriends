package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanionRegistry {

    public static final Map<String, Class<? extends PlayerCompanion>> COMPANIONS = new HashMap<>();

    public static <T extends PlayerCompanion> EntityType<T> registerCompanion(Class<T> entityClass, Identifier id) {

        EntityType.EntityFactory<T> factory = (type, world) -> {
            try {
                return entityClass.getConstructor(EntityType.class, World.class).newInstance(type, world);
            } catch (Exception e) {
                FamiliarFriends.LOGGER.info("Failed to create instance of {}", entityClass.getName(), e);
                return null;
            }
        };

        EntityType<T> entityType = null;
        try {
            entityType = Registry.register(
                    Registries.ENTITY_TYPE,
                    id,
                    FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                            .dimensions(EntityDimensions.fixed(0.4f, 0.4f))
                            .disableSummon()
                            .build()
            );
        } catch (Exception e) {
            FamiliarFriends.LOGGER.info("Failed to register entity type for {}", id, e);
        }

        if (entityType != null) {
            COMPANIONS.put(entityType.getTranslationKey(), entityClass);
        }

        return entityType;
    }

    // Reconstructs a companion from the backupKey when needed
    // There might be a better way to do this, but I don't know what that way would be...
    public static PlayerCompanion createCompanion(String key, PlayerEntity player, boolean gui) {
        try {
            Class<? extends PlayerCompanion> companionClass = CompanionRegistry.COMPANIONS.get(key);
            return companionClass.getConstructor(
                    World.class,
                    PlayerEntity.class,
                    boolean.class
            ).newInstance(
                    player.getWorld(),
                    player,
                    gui
            );

        } catch (Exception e) {
            return null;
        }
    }



    // Used in book GUI so that language determines the order the companions show up in
    // Called on client, but felt it could go here for organization
    public static List<PlayerCompanion> addCompanions(PlayerEntity player) {

        List<PlayerCompanion> companions = new ArrayList<>();

        CompanionRegistry.COMPANIONS.forEach((key, companionClass) -> {
            PlayerCompanion companion = createCompanion(key, player, true);
            companions.add(companion);
        });

        if (companions.isEmpty()) {
            FamiliarFriends.LOGGER.info("No Companions added to the book :<");
        }

        // Sort Alphabetically, taking language into account
        companions.sort((o1, o2) -> {
            String name1 = o1.getName().getString();
            String name2 = o2.getName().getString();
            return name1.compareToIgnoreCase(name2);
        });

        return companions;
    }
}
