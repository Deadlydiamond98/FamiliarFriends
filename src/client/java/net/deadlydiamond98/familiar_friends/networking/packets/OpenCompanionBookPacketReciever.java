package net.deadlydiamond98.familiar_friends.networking.packets;

import net.deadlydiamond98.familiar_friends.networking.packet.OpenCompanionBookPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionDataPacket;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.text.Text;

public class OpenCompanionBookPacketReciever {
    public static void recieve(OpenCompanionBookPacket payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        client.execute(() -> {
            client.setScreen(new CompanionBookScreen(Text.empty()));
        });
    }
}
