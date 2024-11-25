package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.models.CompanionModelTest;
import net.deadlydiamond98.familiar_friends.models.IronGolemCompanionModel;
import net.deadlydiamond98.familiar_friends.models.NaviEntityModel;
import net.deadlydiamond98.familiar_friends.renderer.ChickenCompanionRenderer;
import net.deadlydiamond98.familiar_friends.renderer.CreeperCompanionRenderer;
import net.deadlydiamond98.familiar_friends.renderer.IronGolemCompanionRenderer;
import net.deadlydiamond98.familiar_friends.renderer.NaviCompanionRenderer;
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
		EntityRendererRegistry.register(CompanionEntities.Creeper_Companion, CreeperCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Chicken_Companion, ChickenCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Iron_Golem_Companion, IronGolemCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Navi_Companion, NaviCompanionRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(CompanionModelTest.LAYER_LOCATION, CompanionModelTest::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(IronGolemCompanionModel.LAYER_LOCATION, IronGolemCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NaviEntityModel.LAYER_LOCATION, NaviEntityModel::getTexturedModelData);
	}
}