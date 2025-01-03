package net.deadlydiamond98.familiar_friends.networking;


import net.deadlydiamond98.familiar_friends.networking.packets.OpenCompanionBookPacket;
import net.deadlydiamond98.familiar_friends.networking.packets.SyncCompanionDataPacket;
import net.deadlydiamond98.familiar_friends.networking.packets.SyncPlayerCompanionDataPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import static net.deadlydiamond98.familiar_friends.networking.FamiliarPacketIds.*;

public class CompanionClientPackets {

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(CompanionPlayerSync, SyncPlayerCompanionDataPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(CompanionSync, SyncCompanionDataPacket::recieve);
        ClientPlayNetworking.registerGlobalReceiver(OpenBook, OpenCompanionBookPacket::recieve);
    }

    public static void unlockPlayerCompanion(String companion) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(companion);
        ClientPlayNetworking.send(UnlockCompanion, buf);
    }

    public static void equipPlayerCompanion(String companion) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(companion);
        ClientPlayNetworking.send(EquipCompanion, buf);
    }

    public static void unequipPlayerCompanion() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(UnequipCompanion, buf);
    }

    public static void companionSpecialAbility() {
        PacketByteBuf buf = PacketByteBufs.create();
        ClientPlayNetworking.send(ActionCompanion, buf);
    }

    public static void sendKeybinding(String keybinding) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(keybinding);
        ClientPlayNetworking.send(KeybindingCompanion, buf);
    }

    public static void sendCompanion(String key, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(key);
        buf.writeInt(index);
        ClientPlayNetworking.send(CompanionRequest, buf);
    }
}
