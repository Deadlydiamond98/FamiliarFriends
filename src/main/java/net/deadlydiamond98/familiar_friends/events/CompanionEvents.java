package net.deadlydiamond98.familiar_friends.events;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public class CompanionEvents {

    public static void registerEvents() {
        ServerPlayerEvents.AFTER_RESPAWN.register(new OnPlayerDeathEvent());
        onAttackEvent();
    }

    public static void onAttackEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            PlayerCompanion currentCompanion = player.getCompanion();

            if (currentCompanion != null && entity instanceof LivingEntity livingEntity) {
                currentCompanion.onAttack(player, livingEntity);
            }
            return ActionResult.PASS;
        });
    }
}
