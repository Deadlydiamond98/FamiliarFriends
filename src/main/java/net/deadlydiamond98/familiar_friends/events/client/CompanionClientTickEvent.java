package net.deadlydiamond98.familiar_friends.events.client;

import net.deadlydiamond98.familiar_friends.init.client.CompanionKeybindings;
import net.deadlydiamond98.familiar_friends.networking.c2s.CompanionKeybindC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class CompanionClientTickEvent {
    private static boolean companionActionPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(CompanionClientTickEvent::tick);
    }

    public static void tick(MinecraftClient client) {
        if (client.player != null) {
            if (CompanionKeybindings.COMPANION_ACTION.isPressed()) {
                if (!companionActionPressed) {
                    companionActionPressed = true;
                    CompanionKeybindC2SPacket.send();
                }
            } else {
                companionActionPressed = false;
            }
        }

    }
}
