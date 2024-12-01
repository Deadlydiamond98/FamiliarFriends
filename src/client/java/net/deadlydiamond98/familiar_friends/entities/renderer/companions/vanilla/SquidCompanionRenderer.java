package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SquidCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.SquidCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SquidCompanionRenderer extends CompanionRenderer<SquidCompanion, SquidCompanionModel<SquidCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/squid/squid.png");

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
