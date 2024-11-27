package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.*;
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

    public static void companionSpecialAbility(String keybinding) {
        ClientPlayNetworking.send(new PerformCompanionSpecialAbilityPacket(keybinding));
    }

    public static void sendKeybinding(String keybinding) {
        ClientPlayNetworking.send(new CurrentKeybindPacket(keybinding));
    }

}
