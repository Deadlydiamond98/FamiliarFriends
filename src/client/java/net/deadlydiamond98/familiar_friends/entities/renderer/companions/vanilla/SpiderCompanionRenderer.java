package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpiderCompanionRenderer extends CompanionRenderer<SpiderCompanion, SpiderEntityModel<SpiderCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/spider/spider.png");

    public SpiderCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SpiderEntityModel<>(ctx.getPart(EntityModelLayers.SPIDER)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 0.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.25f);
    }


    @Override
    public Identifier getTexture(SpiderCompanion entity) {
        return TEXTURE;
    }
}
