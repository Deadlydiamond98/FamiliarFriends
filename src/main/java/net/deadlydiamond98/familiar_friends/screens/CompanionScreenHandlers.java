package net.deadlydiamond98.familiar_friends.screens;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CompanionScreenHandlers {
    public static ScreenHandlerType<CompanionBookScreenHandler> COMPANION_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        COMPANION_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(FamiliarFriends.MOD_ID, "companion_book_screen_handler"),
                new ScreenHandlerType<>((CompanionBookScreenHandler::new),
                        FeatureFlags.VANILLA_FEATURES)
        );
    }
}
