package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.SyncUnlockedCompanionsPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.UnlockCompanionPacket;
import net.deadlydiamond98.familiar_friends.networking.packets.SyncUnlockedCompanionsPacketReciever;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.UUID;

public class CompanionClientPackets {

    public static void registerC2SPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SyncUnlockedCompanionsPacket.ID, SyncUnlockedCompanionsPacketReciever::recieve);
    }

    public static void unlockPlayerCompanion(String companion) {
        ClientPlayNetworking.send(new UnlockCompanionPacket(companion));
    }


}
