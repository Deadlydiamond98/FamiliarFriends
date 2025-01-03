package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SkeletonCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.SkeletonCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SkeletonCompanionRenderer extends CompanionRenderer<SkeletonCompanion, SkeletonCompanionModel<SkeletonCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/skeleton/skeleton.png");

    public SkeletonCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SkeletonCompanionModel<>(ctx.getPart(SkeletonCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }


    @Override
    public Identifier getTexture(SkeletonCompanion entity) {
        return TEXTURE;
    }
}
