package net.deadlydiamond98.familiar_friends.entities.renderer;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.BipedCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public abstract class BipedCompanionRenderer<T extends PlayerCompanion> extends CompanionRenderer<T, BipedCompanionModel<T>> {


    public BipedCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedCompanionModel<>(ctx.getPart(BipedCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }
}
