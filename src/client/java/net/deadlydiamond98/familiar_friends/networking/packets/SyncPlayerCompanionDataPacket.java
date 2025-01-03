package net.deadlydiamond98.familiar_friends.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class SyncPlayerCompanionDataPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int listSize = buf.readInt();
        List<String> unlockedCompanions = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            unlockedCompanions.add(buf.readString());
        }
        String currentCompanion = buf.readString();
        int cooldown = buf.readInt();
        client.execute(() -> {

            PlayerEntity player = client.player;

            if (player != null) {
                player.syncUnlockedList(unlockedCompanions);
                player.syncCurrentCompanion(currentCompanion);
                player.syncCompanionCooldown(cooldown);
            }
        });
    }
}
