package net.deadlydiamond98.familiar_friends.client.rendering.companions.vanilla;

import net.deadlydiamond98.familiar_friends.client.models.vanilla.AllayCompanionModel;
import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla.AllayCompanion;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AllayCompanionRenderer extends CompanionRenderer<AllayCompanion, AllayCompanionModel<AllayCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/allay/allay.png");

    public AllayCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AllayCompanionModel<>(ctx.getPart(AllayCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.5f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }


    @Override
    public Identifier getTexture(AllayCompanion entity) {
        return TEXTURE;
    }
}
