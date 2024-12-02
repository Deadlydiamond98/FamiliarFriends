package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record SyncCompanionDataPacket(int cost, boolean enabled, int index) implements CustomPayload {

    public static final Id<SyncCompanionDataPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "companion_sync_packet"));

    public static final PacketCodec<PacketByteBuf, SyncCompanionDataPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SyncCompanionDataPacket::cost,
            PacketCodecs.BOOL, SyncCompanionDataPacket::enabled,
            PacketCodecs.INTEGER, SyncCompanionDataPacket::index,
            SyncCompanionDataPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}