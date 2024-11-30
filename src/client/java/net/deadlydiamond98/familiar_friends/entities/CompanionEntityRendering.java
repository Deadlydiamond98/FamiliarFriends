package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.entities.companions.CirnoCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CaveSpiderCompanion;
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
        EntityRendererRegistry.register(CompanionEntityTypes.Chicken_Companion, ChickenCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Iron_Golem_Companion, IronGolemCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Navi_Companion, NaviCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Ocelot_Companion, OcelotCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Squid_Companion, SquidCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.One_Up_Mushroom_Companion, OneUpMushroomCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Companion_Cube_Companion, CompanionCubeCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Notch_Companion, NotchCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Herobrine_Companion, HerobrineCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Spider_Companion, SpiderCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Skeleton_Companion, SkeletonCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Snow_Golem_Companion, SnowGolemCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Allay_Companion, AllayCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Tiny_Tater_Companion, TinyTaterCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Creeper_Companion, CreeperCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Mr_Saturn_Companion, MrSaturnCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Cave_Spider_Companion, CaveSpiderCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Cirno_Companion, CirnoCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Goat_Companion, GoatCompanionRenderer::new);
        EntityRendererRegistry.register(CompanionEntityTypes.Rana_Companion, RanaCompanionRenderer::new);
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
        EntityModelLayerRegistry.registerModelLayer(TinyTaterModel.LAYER_LOCATION, TinyTaterModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MrSaturnCompanionModel.LAYER_LOCATION, MrSaturnCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CirnoCompanionModel.LAYER_LOCATION, CirnoCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoatCompanionModel.LAYER_LOCATION, GoatCompanionModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RanaCompanionModel.LAYER_LOCATION, RanaCompanionModel::getTexturedModelData);
    }
}
