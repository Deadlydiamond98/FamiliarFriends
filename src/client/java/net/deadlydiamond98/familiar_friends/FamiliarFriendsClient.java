package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.models.*;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.renderer.*;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.screens.CompanionScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FamiliarFriendsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CompanionClientPackets.registerC2SPackets();
		registerEntityRenderers();
		registerModelLayers();

		HandledScreens.register(CompanionScreenHandlers.COMPANION_SCREEN_HANDLER, CompanionBookScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(CompanionEntities.Creeper_Companion, CreeperCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Chicken_Companion, ChickenCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Iron_Golem_Companion, IronGolemCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Navi_Companion, NaviCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Ocelot_Companion, OcelotCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Squid_Companion, SquidCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.One_Up_Mushroom_Companion, OneUpMushroomCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Companion_Cube_Companion, CompanionCubeCompanionRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(CompanionModelTest.LAYER_LOCATION, CompanionModelTest::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(IronGolemCompanionModel.LAYER_LOCATION, IronGolemCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NaviEntityModel.LAYER_LOCATION, NaviEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OneUpMushroomModel.LAYER_LOCATION, OneUpMushroomModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CompanionCubeModel.LAYER_LOCATION, CompanionCubeModel::getTexturedModelData);
	}
}