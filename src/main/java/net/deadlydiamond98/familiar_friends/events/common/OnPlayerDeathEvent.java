package net.deadlydiamond98.familiar_friends.events.common;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.util.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerDeathEvent implements ServerPlayerEvents.AfterRespawn {
    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        PlayerEntity oldP = oldPlayer;
        PlayerEntity newP = newPlayer;

        if (!FamiliarFriendsConfig.Main.lockCompanionsOnDeath) {
            CompanionRegistry.COMPANIONS.forEach((string, aClass) -> {
                if (oldP.isCompanionUnlocked(CompanionRegistry.createCompanion(string, oldP))) {
                    newP.unlockCompanion(string);
                }
            });
        }

        PlayerCompanion companion = oldP.getCompanion();

        if (companion != null && !FamiliarFriendsConfig.Main.unequipCurrentOnDeath) {
            newP.equipCompanion(companion);
        }
    }
}
