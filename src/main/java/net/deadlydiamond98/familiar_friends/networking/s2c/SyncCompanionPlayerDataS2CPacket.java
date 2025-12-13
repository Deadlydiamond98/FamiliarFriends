package net.deadlydiamond98.familiar_friends.networking.s2c;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.screens.CompanionBookScreen;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SyncCompanionPlayerDataS2CPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "companion_player_sync_packet");

    public static void send(ServerPlayerEntity player, List<String> unlockedCompanions, String currentCompanion, int cooldown) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(unlockedCompanions.size());
        for (String companion : unlockedCompanions) {
            buf.writeString(companion);
        }
        buf.writeString(currentCompanion);
        buf.writeInt(cooldown);
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
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
}