package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.models.CompanionModelTest;
import net.deadlydiamond98.familiar_friends.renderer.TestCompanionRenderer;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.screens.CompanionScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerModelLayers();

		HandledScreens.register(CompanionScreenHandlers.COMPANION_SCREEN_HANDLER, CompanionBookScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(CompanionEntities.Player_Companion, TestCompanionRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(CompanionModelTest.LAYER_LOCATION, CompanionModelTest::getTexturedModelData);
	}
}