package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CaveSpiderCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CaveSpiderCompanionRenderer extends CompanionRenderer<CaveSpiderCompanion, SpiderEntityModel<CaveSpiderCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/spider/cave_spider.png");

    public CaveSpiderCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SpiderEntityModel<>(ctx.getPart(EntityModelLayers.CAVE_SPIDER)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 0.525f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.175f);
    }


    @Override
    public Identifier getTexture(CaveSpiderCompanion entity) {
        return TEXTURE;
    }
}
