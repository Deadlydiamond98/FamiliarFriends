package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.UnlockCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.UnlockCompanionPacketReciever;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncUnlockedCompanionsPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class CompanionServerPackets {
    public static void registerServerPackets() {
        PayloadTypeRegistry.playS2C().register(SyncUnlockedCompanionsPacket.ID, SyncUnlockedCompanionsPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UnlockCompanionPacket.ID, UnlockCompanionPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UnlockCompanionPacket.ID, UnlockCompanionPacketReciever::recieve);
    }

    public static void updatePlayerUnlockedCompanions(ServerPlayerEntity player, List<String> unlockedCompanions) {
        ServerPlayNetworking.send(player, new SyncUnlockedCompanionsPacket(unlockedCompanions));
    }

}
