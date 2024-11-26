package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record SyncUnlockedCompanionsPacket(List<String> unlockedCompanions) implements CustomPayload {

    public static final CustomPayload.Id<SyncUnlockedCompanionsPacket> ID = new CustomPayload.Id<>(Identifier.of(FamiliarFriends.MOD_ID, "familiar_sync_packet"));

    public static final PacketCodec<PacketByteBuf, SyncUnlockedCompanionsPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING), SyncUnlockedCompanionsPacket::unlockedCompanions,
            SyncUnlockedCompanionsPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}