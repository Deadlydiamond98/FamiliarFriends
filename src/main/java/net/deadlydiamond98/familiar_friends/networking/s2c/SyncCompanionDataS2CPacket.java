package net.deadlydiamond98.familiar_friends.networking.s2c;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.screens.CompanionBookScreen;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SyncCompanionDataS2CPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "companion_sync_packet");

    public static void send(ServerPlayerEntity player, int cost, boolean enabled, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(cost);
        buf.writeBoolean(enabled);
        buf.writeInt(index);
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
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
}