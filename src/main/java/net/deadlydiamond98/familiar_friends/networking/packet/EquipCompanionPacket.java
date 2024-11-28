package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public record EquipCompanionPacket(String companion) implements CustomPayload {
    public static final Id<EquipCompanionPacket> ID = new Id<>(Identifier.of(FamiliarFriends.MOD_ID, "equip_companion_packet"));

    public static final PacketCodec<PacketByteBuf, EquipCompanionPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, EquipCompanionPacket::companion,
            EquipCompanionPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void recieve(EquipCompanionPacket payload, ServerPlayNetworking.Context context) {
        String companion = payload.companion();
        MinecraftServer server = context.server();
        server.execute(() -> {

            PlayerCompanion playerCompanion = CompanionRegistry.createCompanion(companion, context.player());

            if (playerCompanion != null) {
                PlayerEntity player = context.player();
                player.grantCompanion(playerCompanion);
                player.getWorld().spawnEntity(playerCompanion);
            }

        });
    }
}
