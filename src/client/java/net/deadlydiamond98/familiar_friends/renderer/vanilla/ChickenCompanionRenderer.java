package net.deadlydiamond98.familiar_friends.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.models.vanilla.ChickenCompanionModel;
import net.deadlydiamond98.familiar_friends.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChickenCompanionRenderer extends CompanionRenderer<ChickenCompanion, ChickenCompanionModel<ChickenCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/chicken.png");

    public ChickenCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChickenCompanionModel<>(ctx.getPart(ChickenCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 2.5f);
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
