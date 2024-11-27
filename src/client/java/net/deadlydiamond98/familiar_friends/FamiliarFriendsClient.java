package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.companions.TaterCompanion;
import net.deadlydiamond98.familiar_friends.events.CompanionClientTickEvent;
import net.deadlydiamond98.familiar_friends.models.*;
import net.deadlydiamond98.familiar_friends.models.vanilla.BipedCompanionModel;
import net.deadlydiamond98.familiar_friends.models.vanilla.ChickenCompanionModel;
import net.deadlydiamond98.familiar_friends.models.vanilla.IronGolemCompanionModel;
import net.deadlydiamond98.familiar_friends.models.vanilla.OcelotCompanionModel;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.renderer.*;
import net.deadlydiamond98.familiar_friends.renderer.player.HerobrineCompanionRenderer;
import net.deadlydiamond98.familiar_friends.renderer.player.NotchCompanionRenderer;
import net.deadlydiamond98.familiar_friends.renderer.vanilla.*;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.screens.CompanionScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FamiliarFriendsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CompanionClientTickEvent.registerTickEvent();
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
		EntityRendererRegistry.register(CompanionEntities.Notch_Companion, NotchCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Herobrine_Companion, HerobrineCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Spider_Companion, SpiderCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Skeleton_Companion, SkeletonCompanionRenderer::new);
		EntityRendererRegistry.register(CompanionEntities.Tater_Companion, TaterCompanionRenderer::new);
	}

	public void registerModelLayers() {
		EntityModelLayerRegistry.registerModelLayer(CompanionModelTest.LAYER_LOCATION, CompanionModelTest::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(IronGolemCompanionModel.LAYER_LOCATION, IronGolemCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NaviEntityModel.LAYER_LOCATION, NaviEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OneUpMushroomModel.LAYER_LOCATION, OneUpMushroomModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CompanionCubeModel.LAYER_LOCATION, CompanionCubeModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BipedCompanionModel.LAYER_LOCATION, BipedCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ChickenCompanionModel.LAYER_LOCATION, ChickenCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OcelotCompanionModel.LAYER_LOCATION, OcelotCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SkeletonCompanionModel.LAYER_LOCATION, SkeletonCompanionModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LilTaterCompanionModel.LAYER_LOCATION, LilTaterCompanionModel::getTexturedModelData);
	}
}