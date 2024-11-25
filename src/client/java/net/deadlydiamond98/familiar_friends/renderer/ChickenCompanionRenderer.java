package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChickenCompanionRenderer extends CompanionRenderer<ChickenCompanion, ChickenEntityModel<ChickenCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/chicken.png");

    public ChickenCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChickenEntityModel<>(ctx.getPart(EntityModelLayers.CHICKEN)));
    }

    @Override
    protected void setupTransforms(ChickenCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

    }

    @Override
    protected void bookScale(MatrixStack matrices) {
        this.scale(matrices, 1.5f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.75f);
    }


    @Override
    public Identifier getTexture(ChickenCompanion entity) {
        return TEXTURE;
    }
}
