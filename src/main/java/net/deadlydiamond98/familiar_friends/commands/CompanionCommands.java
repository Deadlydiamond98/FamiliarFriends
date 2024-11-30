package net.deadlydiamond98.familiar_friends.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.Iterator;

public class CompanionCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            unlockAllCompanions(dispatcher, registryAccess, environment);
        });
    }

    private static void unlockAllCompanions(CommandDispatcher<ServerCommandSource> dispatcher,
                                            CommandRegistryAccess registryAccess,
                                            CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("lockcompanions").requires((source) -> {
                    return source.hasPermissionLevel(2);
                })
                .then(CommandManager.argument("targets", EntityArgumentType.players()).executes((context) -> {

                    return executeLock((ServerCommandSource)context.getSource(), EntityArgumentType.getEntities(context, "targets"));

                })));
    }

    private static int executeLock(ServerCommandSource source, Collection<? extends Entity> targets) {

        Iterator targetss = targets.iterator();

        while(targetss.hasNext()) {
            PlayerEntity serverPlayerEntity = (PlayerEntity) targetss.next();
            serverPlayerEntity.lockAllCompanions();
        }
        return targets.size();
    }
}
