package net.deadlydiamond98.familiar_friends.networking.packet;

import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.UUID;

public class UnlockCompanionPacketReciever {

    public static void recieve(UnlockCompanionPacket payload, ServerPlayNetworking.Context context) {
        String companion = payload.companion();
        MinecraftServer server = context.server();
        server.execute(() -> {
            ((CompanionPlayerData) context.player()).unlockCompanion(companion);
        });
    }
}
