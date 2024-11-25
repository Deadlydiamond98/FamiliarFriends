package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.models.CompanionModelTest;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class TestCompanionRenderer extends CompanionRenderer<PlayerCompanion, CreeperEntityModel<PlayerCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/creeper/creeper.png");

    public TestCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CreeperEntityModel<>(ctx.getPart(EntityModelLayers.CREEPER)));
    }

    @Override
    protected void setupTransforms(PlayerCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

    }

    @Override
    protected void scale(PlayerCompanion entity, MatrixStack matrices, float amount) {
        matrices.scale(0.25f, 0.25f, 0.25f);
    }

    @Override
    public Identifier getTexture(PlayerCompanion entity) {
        return TEXTURE;
    }
}
