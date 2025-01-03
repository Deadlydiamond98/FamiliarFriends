package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.OcelotCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.OcelotCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OcelotCompanionRenderer extends CompanionRenderer<OcelotCompanion, OcelotCompanionModel<OcelotCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/cat/ocelot.png");

    public OcelotCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OcelotCompanionModel<>(ctx.getPart(OcelotCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(OcelotCompanion entity) {
        return TEXTURE;
    }
}
