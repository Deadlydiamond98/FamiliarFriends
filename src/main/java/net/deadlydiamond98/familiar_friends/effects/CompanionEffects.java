package net.deadlydiamond98.familiar_friends.effects;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CompanionEffects {
    public static final RegistryEntry<StatusEffect> SUPER = registerStatusEffect("super",
            new SuperMushroomEffect(StatusEffectCategory.BENEFICIAL, 16712965)
                    .addAttributeModifier(EntityAttributes.GENERIC_SCALE,
                            Identifier.of(FamiliarFriends.MOD_ID,"super"), 2,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                            Identifier.of(FamiliarFriends.MOD_ID,"super"), 4,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                            Identifier.of(FamiliarFriends.MOD_ID,"super"), 4,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_STEP_HEIGHT,
                            Identifier.of(FamiliarFriends.MOD_ID,"super"), 3,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,
                            Identifier.of(FamiliarFriends.MOD_ID,"super"), 6,
                            EntityAttributeModifier.Operation.ADD_VALUE)
    );

    public static final RegistryEntry<StatusEffect> MINI = registerStatusEffect("mini",
            new SuperMushroomEffect(StatusEffectCategory.BENEFICIAL, 4056063)
                    .addAttributeModifier(EntityAttributes.GENERIC_SCALE,
                            Identifier.of(FamiliarFriends.MOD_ID,"mini"), -.65,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_GRAVITY,
                            Identifier.of(FamiliarFriends.MOD_ID,"mini"), -.05,
                            EntityAttributeModifier.Operation.ADD_VALUE)
    );



    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(FamiliarFriends.MOD_ID, name), statusEffect);
    }

    public static void registerEffects(){
        FamiliarFriends.LOGGER.info("Registering Mod Effects for " + FamiliarFriends.MOD_ID);
    }
}
