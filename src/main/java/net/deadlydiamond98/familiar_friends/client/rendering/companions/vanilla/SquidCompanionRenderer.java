package net.deadlydiamond98.familiar_friends.client.rendering.companions.vanilla;

import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla.SquidCompanion;
import net.deadlydiamond98.familiar_friends.client.models.vanilla.SquidCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SquidCompanionRenderer extends CompanionRenderer<SquidCompanion, SquidCompanionModel<SquidCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/squid/squid.png");

    public SquidCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SquidCompanionModel<>(ctx.getPart(SquidCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 0.70f);
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
