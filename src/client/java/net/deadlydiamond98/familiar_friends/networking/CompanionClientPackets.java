package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.EquipCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionDataPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.UnlockCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packets.SyncCompanionDataPacketReciever;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CompanionClientPackets {

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SyncCompanionDataPacket.ID, SyncCompanionDataPacketReciever::recieve);
    }

    public static void unlockPlayerCompanion(String companion) {
        ClientPlayNetworking.send(new UnlockCompanionPacket(companion));
    }

    public static void equipPlayerCompanion(String companion) {
        ClientPlayNetworking.send(new EquipCompanionPacket(companion));
    }

}
