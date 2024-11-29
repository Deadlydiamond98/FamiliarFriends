package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.entities.models.*;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.*;
import net.deadlydiamond98.familiar_friends.entities.renderer.*;
import net.deadlydiamond98.familiar_friends.entities.renderer.player.HerobrineCompanionRenderer;
import net.deadlydiamond98.familiar_friends.entities.renderer.player.NotchCompanionRenderer;
import net.deadlydiamond98.familiar_friends.entities.renderer.vanilla.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CompanionEntityRendering {

    public static void registerCompanionRenderers() {
        registerEntityRenderers();
        registerModelLayers();
    }

    private static void registerEntityRenderers() {
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
        EntityRendererRegistry.register(CompanionEntities.Snow_Golem_Companion, SnowGolemCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntities.Allay_Companion, AllayCompanionRenderer::new);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(IronGolemCompanionModel.LAYER_LOCATION, IronGolemCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(NaviEntityModel.LAYER_LOCATION, NaviEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(OneUpMushroomModel.LAYER_LOCATION, OneUpMushroomModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CompanionCubeModel.LAYER_LOCATION, CompanionCubeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BipedCompanionModel.LAYER_LOCATION, BipedCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ChickenCompanionModel.LAYER_LOCATION, ChickenCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(OcelotCompanionModel.LAYER_LOCATION, OcelotCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SkeletonCompanionModel.LAYER_LOCATION, SkeletonCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AllayCompanionModel.LAYER_LOCATION, AllayCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SquidCompanionModel.LAYER_LOCATION, SquidCompanionModel::getTexturedModelData);
    }
}
