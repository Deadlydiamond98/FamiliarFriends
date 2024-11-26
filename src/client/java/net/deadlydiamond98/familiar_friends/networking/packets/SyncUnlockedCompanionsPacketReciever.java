package net.deadlydiamond98.familiar_friends.networking.packets;

import net.deadlydiamond98.familiar_friends.networking.packet.SyncUnlockedCompanionsPacket;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class SyncUnlockedCompanionsPacketReciever {
    public static void recieve(SyncUnlockedCompanionsPacket payload, ClientPlayNetworking.Context context) {
        List<String> unlockedCompanions = payload.unlockedCompanions();
        MinecraftClient client = context.client();
        client.execute(() -> {
            if (client.player != null) {
                ((CompanionPlayerData) client.player).syncUnlockedList(unlockedCompanions);
            }
        });
    }
}
