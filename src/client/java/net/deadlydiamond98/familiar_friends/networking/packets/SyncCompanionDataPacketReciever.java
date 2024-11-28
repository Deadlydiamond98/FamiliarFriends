package net.deadlydiamond98.familiar_friends.networking.packets;

import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionDataPacket;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class SyncCompanionDataPacketReciever {
    public static void recieve(SyncCompanionDataPacket payload, ClientPlayNetworking.Context context) {
        List<String> unlockedCompanions = payload.unlockedCompanions();
        String currentCompanion = payload.currentCompanion();
        MinecraftClient client = context.client();
        client.execute(() -> {

            PlayerEntity player = client.player;

            if (player != null) {
                player.syncUnlockedList(unlockedCompanions);
                player.syncCurrentCompanion(currentCompanion);
            }
        });
    }
}
