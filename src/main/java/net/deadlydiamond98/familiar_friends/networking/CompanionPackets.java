package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.c2s.*;
import net.deadlydiamond98.familiar_friends.networking.s2c.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class CompanionPackets {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(OpenCompanionBookS2CPacket.ID, OpenCompanionBookS2CPacket.Handler::receive);
        ClientPlayNetworking.registerGlobalReceiver(SyncCompanionDataS2CPacket.ID, SyncCompanionDataS2CPacket.Handler::receive);
        ClientPlayNetworking.registerGlobalReceiver(SyncCompanionPlayerDataS2CPacket.ID, SyncCompanionPlayerDataS2CPacket.Handler::receive);
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EquipCompanionC2SPacket.ID, EquipCompanionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(UnequipCompanionC2SPacket.ID, UnequipCompanionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(CompanionKeybindC2SPacket.ID, CompanionKeybindC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(UnlockCompanionC2SPacket.ID, UnlockCompanionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(RequestCompanionC2SPacket.ID, RequestCompanionC2SPacket.Handler::receive);
    }
}
