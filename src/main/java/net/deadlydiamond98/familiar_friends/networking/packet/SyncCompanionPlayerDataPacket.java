package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record SyncCompanionPlayerDataPacket(List<String> unlockedCompanions, String currentCompanion, int cooldown) implements CustomPayload {

    public static final CustomPayload.Id<SyncCompanionPlayerDataPacket> ID = new CustomPayload.Id<>(Identifier.of(FamiliarFriends.MOD_ID, "companion_player_sync_packet"));

    public static final PacketCodec<PacketByteBuf, SyncCompanionPlayerDataPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING), SyncCompanionPlayerDataPacket::unlockedCompanions,
            PacketCodecs.STRING, SyncCompanionPlayerDataPacket::currentCompanion,
            PacketCodecs.INTEGER, SyncCompanionPlayerDataPacket::cooldown,
            SyncCompanionPlayerDataPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}