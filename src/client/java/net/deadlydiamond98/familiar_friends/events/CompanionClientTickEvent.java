package net.deadlydiamond98.familiar_friends.events;

import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CompanionClientTickEvent {
    public static final String COMPANION_KEY_CATEGORY = "key.category.familiar_friends.companion_keys";

    public static KeyBinding companionAction;

    private static boolean hasFired = false;

    public static void endTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player != null) {

                if (companionAction.isPressed()) {
                    if (!hasFired) {
                        hasFired = true;
                        CompanionClientPackets.companionSpecialAbility();
                    }
                } else {
                    hasFired = false;
                }
            }

        });

    }

    public static void registerTickEvent() {
        registerKeybindings();
        endTickEvent();
    }

    public static void registerKeybindings() {
        companionAction = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.familiar_friends.action",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                COMPANION_KEY_CATEGORY
        ));
    }

    public static String getKeybinding() {
        return CompanionClientTickEvent.companionAction.getBoundKeyLocalizedText().getString();
    }
}
