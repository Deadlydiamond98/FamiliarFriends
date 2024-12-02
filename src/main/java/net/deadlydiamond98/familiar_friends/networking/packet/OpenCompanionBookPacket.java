package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenCompanionBookPacket(boolean ignore) implements CustomPayload {

    public static final Id<OpenCompanionBookPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "open_companion_book_packet"));

    public static final PacketCodec<PacketByteBuf, OpenCompanionBookPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, OpenCompanionBookPacket::ignore,
            OpenCompanionBookPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}