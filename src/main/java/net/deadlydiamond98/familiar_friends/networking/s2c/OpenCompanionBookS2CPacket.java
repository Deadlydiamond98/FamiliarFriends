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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OpenCompanionBookS2CPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "open_companion_book_packet");

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, ID, PacketByteBufs.create());
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            client.execute(() -> client.setScreen(new CompanionBookScreen(Text.empty())));
        }
    }
}