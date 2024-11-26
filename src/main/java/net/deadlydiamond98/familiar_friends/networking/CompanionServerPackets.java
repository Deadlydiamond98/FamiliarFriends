package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.EquipCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.UnlockCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionDataPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class CompanionServerPackets {
    public static void registerServerPackets() {
        PayloadTypeRegistry.playS2C().register(SyncCompanionDataPacket.ID, SyncCompanionDataPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UnlockCompanionPacket.ID, UnlockCompanionPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(EquipCompanionPacket.ID, EquipCompanionPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UnlockCompanionPacket.ID, UnlockCompanionPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(EquipCompanionPacket.ID, EquipCompanionPacket::recieve);
    }

    public static void syncCompanionPlayerData(ServerPlayerEntity player, List<String> unlockedCompanions, String currentCompanion) {
        ServerPlayNetworking.send(player, new SyncCompanionDataPacket(unlockedCompanions, currentCompanion));
    }

}
