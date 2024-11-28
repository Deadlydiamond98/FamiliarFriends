package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public record UnlockCompanionPacket(String companion) implements CustomPayload {
    public static final CustomPayload.Id<UnlockCompanionPacket> ID = new CustomPayload.Id<>(Identifier.of(FamiliarFriends.MOD_ID, "unlock_companion_packet"));

    public static final PacketCodec<PacketByteBuf, UnlockCompanionPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, UnlockCompanionPacket::companion,
            UnlockCompanionPacket::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void recieve(UnlockCompanionPacket payload, ServerPlayNetworking.Context context) {
        String companion = payload.companion();
        MinecraftServer server = context.server();
        server.execute(() -> {
            PlayerEntity player = context.player();
            int levels = CompanionRegistry.createCompanion(companion, player, false).getCost();
            player.addExperienceLevels(-levels);
            player.unlockCompanion(companion);
        });
    }
}
