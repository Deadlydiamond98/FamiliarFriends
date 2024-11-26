package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.SquidCompanion;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SquidCompanionRenderer extends CompanionRenderer<SquidCompanion, SquidEntityModel<SquidCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/squid/squid.png");

    public SquidCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SquidEntityModel<>(ctx.getPart(EntityModelLayers.SQUID)));
    }

    @Override
    protected void setupTransforms(SquidCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

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
    public Identifier getTexture(SquidCompanion entity) {
        return TEXTURE;
    }
}
