package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public record CurrentKeybindPacket(String keybinding) implements CustomPayload {
    public static final Id<CurrentKeybindPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "keybinding_companion_packet"));

    public static final PacketCodec<PacketByteBuf, CurrentKeybindPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, CurrentKeybindPacket::keybinding,
            CurrentKeybindPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void recieve(CurrentKeybindPacket payload, ServerPlayNetworking.Context context) {
        String keybinding = payload.keybinding();
        MinecraftServer server = context.server();
        server.execute(() -> {
            FamiliarFriends.Current_Keybinding_Key = keybinding;
        });
    }
}
