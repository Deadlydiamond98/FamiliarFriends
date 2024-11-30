package net.deadlydiamond98.familiar_friends.events;

import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerDeathEvent implements ServerPlayerEvents.AfterRespawn {
    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        PlayerEntity oldP = oldPlayer;
        PlayerEntity newP = newPlayer;

        CompanionRegistry.COMPANIONS.forEach((string, aClass) -> {
            if (oldP.isCompanionUnlocked(CompanionRegistry.createCompanion(string, oldP))) {
                newP.unlockCompanion(string);
            }
        });

        PlayerCompanion companion = oldP.getCompanion();

        if (companion != null) {
            newP.equipCompanion(companion);
        }
    }
}
