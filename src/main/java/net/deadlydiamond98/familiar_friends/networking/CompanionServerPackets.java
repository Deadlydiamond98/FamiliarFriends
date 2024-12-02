package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class CompanionServerPackets {
    public static void registerServerPackets() {
        //SERVER TO CLIENT
        PayloadTypeRegistry.playS2C().register(SyncCompanionPlayerDataPacket.ID, SyncCompanionPlayerDataPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncCompanionDataPacket.ID, SyncCompanionDataPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenCompanionBookPacket.ID, OpenCompanionBookPacket.CODEC);

        //CLIENT TO SERVER
        PayloadTypeRegistry.playC2S().register(UnlockCompanionPacket.ID, UnlockCompanionPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(EquipCompanionPacket.ID, EquipCompanionPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(UnequipCompanionPacket.ID, UnequipCompanionPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PerformCompanionSpecialAbilityPacket.ID, PerformCompanionSpecialAbilityPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(CurrentKeybindPacket.ID, CurrentKeybindPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(RequestCompanionDataPacket.ID, RequestCompanionDataPacket.CODEC);

        //Reciever
        ServerPlayNetworking.registerGlobalReceiver(UnlockCompanionPacket.ID, UnlockCompanionPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(EquipCompanionPacket.ID, EquipCompanionPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(UnequipCompanionPacket.ID, UnequipCompanionPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(PerformCompanionSpecialAbilityPacket.ID, PerformCompanionSpecialAbilityPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(CurrentKeybindPacket.ID, CurrentKeybindPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(RequestCompanionDataPacket.ID, RequestCompanionDataPacket::recieve);
    }

    public static void syncCompanionPlayerData(ServerPlayerEntity player, List<String> unlockedCompanions, String currentCompanion) {
        ServerPlayNetworking.send(player, new SyncCompanionPlayerDataPacket(unlockedCompanions, currentCompanion));
    }

    public static void syncCompanionData(ServerPlayerEntity player, int cost, boolean enabled, int index) {
        ServerPlayNetworking.send(player, new SyncCompanionDataPacket(cost, enabled, index));
    }

    public static void openCompanionBookScreen(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new OpenCompanionBookPacket(true));
    }
}
