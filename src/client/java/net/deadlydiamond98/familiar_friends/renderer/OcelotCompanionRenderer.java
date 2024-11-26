package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.entities.companions.IronGolemCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OcelotCompanion;
import net.deadlydiamond98.familiar_friends.models.IronGolemCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OcelotCompanionRenderer extends CompanionRenderer<OcelotCompanion, OcelotEntityModel<OcelotCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/cat/ocelot.png");

    public OcelotCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OcelotEntityModel<>(ctx.getPart(EntityModelLayers.OCELOT)));
    }

    @Override
    protected void setupTransforms(OcelotCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

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
    public Identifier getTexture(OcelotCompanion entity) {
        return TEXTURE;
    }
}
