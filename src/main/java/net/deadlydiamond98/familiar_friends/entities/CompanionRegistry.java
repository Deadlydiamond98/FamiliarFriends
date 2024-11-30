package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanionRegistry {

    public static final Map<String, Class<? extends PlayerCompanion>> COMPANIONS = new HashMap<>();

    /**
     *
     * This class is used to register your entities. When calling this class, your entity will be registered,
     * and it will add the companion to the book's GUI
     *
     * @param entityClass The class of an entity being registered
     * @param id The Identifier for the entity, this will be used for assigning the name, author, and description of the
     *           entity in the lang file
     *
     * @return returns the EntityType for an entity
     */
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

    /**
     * Creates a companion for spawning in the world based on a translation key
     * @param key The Translation key for the entity you're trying to retrieve
     * @param player The player entity, used for creating the companion and assigning the companion an owner and world
     * @return returns the companion entity corresponding to the key, if the key doesn't match an existing entity, returns null
     */
    public static PlayerCompanion createCompanion(String key, PlayerEntity player) {
        return createCompanion(key, player, false);
    }

    /**
     * Creates a companion for spawning in the world based on a translation key, you shouldn't use this one
     *
     * @param key The Translation key for the entity you're trying to retrieve
     * @param player The player entity, used for creating the companion and assigning the companion an owner and world
     * @param gui Whether the companion is rendering in the Book gui
     * @return returns the companion entity corresponding to the key, if the key doesn't match an existing entity, returns null
     */
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



    /**
     * Creates a list of companions, and then sorts the companions based on their name (with language taken into account).
     * This is used for the Book gui
     * @param player The player entity, used for creating the companion and assigning the companion an owner and world
     */
    public static List<PlayerCompanion> addCompanionsToBook(PlayerEntity player) {

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
