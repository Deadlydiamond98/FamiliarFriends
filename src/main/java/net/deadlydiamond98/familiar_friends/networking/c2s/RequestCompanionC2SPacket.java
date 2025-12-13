package net.deadlydiamond98.familiar_friends.networking.c2s;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.s2c.SyncCompanionDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;


public class RequestCompanionC2SPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "companion_request_packet");

    public static void send(String key, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(key);
        buf.writeInt(index);
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            String key = buf.readString();
            int index = buf.readInt();
            server.execute(() -> {
                PlayerCompanion companion = CompanionRegistry.createCompanion(key, player);
                SyncCompanionDataS2CPacket.send(player, companion.getCost(), companion.isEnabled(), index);
            });
        }
    }
}
