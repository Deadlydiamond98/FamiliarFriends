package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.IronGolemCompanion;
import net.deadlydiamond98.familiar_friends.models.IronGolemCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class IronGolemCompanionRenderer extends CompanionRenderer<IronGolemCompanion, IronGolemCompanionModel<IronGolemCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/iron_golem/iron_golem.png");

    public IronGolemCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IronGolemCompanionModel<>(ctx.getPart(IronGolemCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void setupTransforms(IronGolemCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

    }

    @Override
    protected void bookScale(MatrixStack matrices) {
       this.scale(matrices, 0.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.25f);
    }


    @Override
    public Identifier getTexture(IronGolemCompanion entity) {
        return TEXTURE;
    }
}
