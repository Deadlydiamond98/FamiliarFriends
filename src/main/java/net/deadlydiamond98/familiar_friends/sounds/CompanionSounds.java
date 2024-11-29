package net.deadlydiamond98.familiar_friends.sounds;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CompanionSounds {

    public static SoundEvent One_Up = registerSoundEvent("one_up");
    public static SoundEvent Mr_Saturn = registerSoundEvent("mr_saturn_shield_break");
    public static SoundEvent Navi = registerSoundEvent("navi_attention");
    public static SoundEvent Action_Failed = registerSoundEvent("action_failed");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(FamiliarFriends.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        FamiliarFriends.LOGGER.info("registering Sounds for " + FamiliarFriends.MOD_ID);
    }
}
