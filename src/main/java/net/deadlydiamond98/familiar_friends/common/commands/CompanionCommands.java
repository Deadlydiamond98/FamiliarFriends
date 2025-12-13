package net.deadlydiamond98.familiar_friends.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;

public class CompanionCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(CompanionCommands::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> commandStart = CommandManager.literal("familiar").requires((source) -> source.hasPermissionLevel(2));
        RequiredArgumentBuilder<ServerCommandSource, EntitySelector> players = CommandManager.argument("targets", EntityArgumentType.players());

        dispatcher.register(commandStart.then(CommandManager.literal("lockAll").then(players).executes(context -> {
            Collection<? extends PlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
            for (PlayerEntity target : targets) {
                target.lockAllCompanions();
            }
            return targets.size();
        })));
    }
}
