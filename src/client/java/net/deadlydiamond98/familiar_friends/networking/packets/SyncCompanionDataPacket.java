package net.deadlydiamond98.familiar_friends.networking.packets;

import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncCompanionDataPacket {

    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int cost = buf.readInt();
        boolean enabled = buf.readBoolean();
        int index = buf.readInt();
        client.execute(() -> {
            if (client.currentScreen instanceof CompanionBookScreen bookScreen) {
                bookScreen.syncCompanionData(cost, enabled, index);
            }
        });
    }
}
