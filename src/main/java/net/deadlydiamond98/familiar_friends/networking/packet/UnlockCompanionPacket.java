package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record UnlockCompanionPacket(String companion) implements CustomPayload {
    public static final CustomPayload.Id<UnlockCompanionPacket> ID = new CustomPayload.Id<>(Identifier.of(FamiliarFriends.MOD_ID, "unlock_familiar_packet"));

    public static final PacketCodec<PacketByteBuf, UnlockCompanionPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, UnlockCompanionPacket::companion,
            UnlockCompanionPacket::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
