package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.networking.packet.*;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

import static net.deadlydiamond98.familiar_friends.networking.FamiliarPacketIds.*;

public class CompanionServerPackets {

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(UnlockCompanion, UnlockCompanionPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(EquipCompanion, EquipCompanionPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(UnequipCompanion, UnequipCompanionPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ActionCompanion, PerformCompanionSpecialAbilityPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(KeybindingCompanion, CurrentKeybindPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CompanionRequest, RequestCompanionDataPacket::receive);
    }

    public static void syncCompanionPlayerData(ServerPlayerEntity player, List<String> unlockedCompanions, String currentCompanion, int cooldown) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(unlockedCompanions.size());
        for (String companion : unlockedCompanions) {
            buf.writeString(companion);
        }
        buf.writeString(currentCompanion);
        buf.writeInt(cooldown);

        ServerPlayNetworking.send(player, CompanionPlayerSync, buf);
    }

    public static void syncCompanionData(ServerPlayerEntity player, int cost, boolean enabled, int index) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(cost);
        buf.writeBoolean(enabled);
        buf.writeInt(index);
        ServerPlayNetworking.send(player, CompanionSync, buf);
    }

    public static void openCompanionBookScreen(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, OpenBook, buf);
    }
}
