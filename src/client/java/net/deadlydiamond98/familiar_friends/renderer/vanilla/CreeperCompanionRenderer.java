package net.deadlydiamond98.familiar_friends.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperCompanionRenderer extends CompanionRenderer<CreeperCompanion, CreeperEntityModel<CreeperCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/creeper/creeper.png");

    public CreeperCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CreeperEntityModel<>(ctx.getPart(EntityModelLayers.CREEPER)));
    }

    @Override
    protected void setupTransforms(CreeperCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.0f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.25f);
    }


    @Override
    public Identifier getTexture(CreeperCompanion entity) {
        return TEXTURE;
    }
}
