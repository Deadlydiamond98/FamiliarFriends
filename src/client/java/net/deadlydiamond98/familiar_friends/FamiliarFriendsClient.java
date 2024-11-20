package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.models.TestPlayerCompanionModel;
import net.deadlydiamond98.familiar_friends.renderer.TestPlayerCompanionEntityRenderer;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.screens.CompanionScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerModelLayers();

		HandledScreens.register(CompanionScreenHandlers.COMPANION_SCREEN_HANDLER, CompanionBookScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(CompanionEntities.Player_Companion, TestPlayerCompanionEntityRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(TestPlayerCompanionModel.LAYER_LOCATION, TestPlayerCompanionModel::getTexturedModelData);
	}
}