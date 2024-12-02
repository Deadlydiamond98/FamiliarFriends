package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public record RequestCompanionDataPacket(String key, int index) implements CustomPayload {

    public static final Id<RequestCompanionDataPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "companion_request_packet"));

    public static final PacketCodec<PacketByteBuf, RequestCompanionDataPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, RequestCompanionDataPacket::key,
            PacketCodecs.INTEGER, RequestCompanionDataPacket::index,
            RequestCompanionDataPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void recieve(RequestCompanionDataPacket payload, ServerPlayNetworking.Context context) {
        MinecraftServer server = context.server();
        server.execute(() -> {
            PlayerCompanion companion = CompanionRegistry.createCompanion(payload.key, context.player());
            CompanionServerPackets.syncCompanionData(context.player(), companion.getCost(), companion.isEnabled(), payload.index);
        });
    }
}