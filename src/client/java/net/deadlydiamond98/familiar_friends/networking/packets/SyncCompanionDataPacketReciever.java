package net.deadlydiamond98.familiar_friends.networking.packets;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionDataPacket;
import net.deadlydiamond98.familiar_friends.networking.packet.SyncCompanionPlayerDataPacket;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class SyncCompanionDataPacketReciever {
    public static void recieve(SyncCompanionDataPacket payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        client.execute(() -> {
            FamiliarFriends.LOGGER.info("TEST");
            if (client.currentScreen instanceof CompanionBookScreen bookScreen) {
                bookScreen.syncCompanionData(payload.cost(), payload.enabled(), payload.index());
            }
        });
    }
}
