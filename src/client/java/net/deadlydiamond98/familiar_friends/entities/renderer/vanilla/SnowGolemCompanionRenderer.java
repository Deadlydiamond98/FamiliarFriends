package net.deadlydiamond98.familiar_friends.entities.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SnowGolemCompanion;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnowGolemCompanionRenderer extends CompanionRenderer<SnowGolemCompanion, SnowGolemEntityModel<SnowGolemCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/snow_golem.png");

    public SnowGolemCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SnowGolemEntityModel<>(ctx.getPart(EntityModelLayers.SNOW_GOLEM)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.0f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.25f);
    }


    @Override
    public Identifier getTexture(SnowGolemCompanion entity) {
        return TEXTURE;
    }
}
