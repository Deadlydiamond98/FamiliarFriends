package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.BookCompanionRegistry;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
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

            for (Class<? extends PlayerCompanion> companionClass : BookCompanionRegistry.COMPANIONS) {
                try {
                    PlayerCompanion companionInstance = companionClass.getConstructor(
                            World.class,
                            PlayerEntity.class,
                            boolean.class
                    ).newInstance(
                            context.player().getWorld(),
                            context.player(),
                            false
                    );

                    if (companionInstance.getType().getTranslationKey().equals(companion)) {

                        ((CompanionPlayerData) context.player()).grantCompanion(companionInstance);
                        ((CompanionPlayerData) context.player()).setCompanionStatus(true);
                        context.player().getWorld().spawnEntity(companionInstance);

                    }

                } catch (Exception ignore) {

                }
            }
        });
    }
}
