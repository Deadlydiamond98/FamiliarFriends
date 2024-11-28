package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public record UnequipCompanionPacket(String companion) implements CustomPayload {
    public static final Id<UnequipCompanionPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "unequip_companion_packet"));

    public static final PacketCodec<PacketByteBuf, UnequipCompanionPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, UnequipCompanionPacket::companion,
            UnequipCompanionPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void recieve(UnequipCompanionPacket payload, ServerPlayNetworking.Context context) {
        MinecraftServer server = context.server();
        server.execute(() -> {

            PlayerEntity player = context.player();
            PlayerCompanion playerCompanion = player.getCompanion();

            if (playerCompanion != null) {
                player.unequipCompanion(playerCompanion);
            }

        });
    }
}
