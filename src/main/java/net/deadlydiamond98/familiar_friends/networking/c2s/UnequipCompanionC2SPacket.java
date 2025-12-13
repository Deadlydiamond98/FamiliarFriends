package net.deadlydiamond98.familiar_friends.networking.c2s;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UnequipCompanionC2SPacket {
    public static final Identifier ID = new Identifier(FamiliarFriends.MOD_ID, "unequip_companion_packet");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.create());
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            server.execute(() -> {
                PlayerCompanion playerCompanion = ((PlayerEntity) player).getCompanion();

                if (playerCompanion != null) {
                    ((PlayerEntity) player).unequipCompanion(playerCompanion);
                }
            });
        }
    }
}
