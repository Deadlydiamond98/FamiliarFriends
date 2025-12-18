package net.deadlydiamond98.familiar_friends.networking.c2s;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.util.CompanionRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;


public class UnlockCompanionC2SPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "unlock_companion_packet");

    public static void send(String companion) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(companion);
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            String companion = buf.readString();
            server.execute(() -> {
                int levels = CompanionRegistry.createCompanion(companion, player).getCost();
                player.addExperienceLevels(-levels);
                ((PlayerEntity) player).unlockCompanion(companion);
            });
        }
    }
}
