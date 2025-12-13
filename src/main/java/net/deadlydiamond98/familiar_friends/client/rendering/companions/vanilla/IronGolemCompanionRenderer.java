package net.deadlydiamond98.familiar_friends.client.rendering.companions.vanilla;

import net.deadlydiamond98.familiar_friends.client.models.vanilla.IronGolemCompanionModel;
import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla.IronGolemCompanion;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class IronGolemCompanionRenderer extends CompanionRenderer<IronGolemCompanion, IronGolemCompanionModel<IronGolemCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/iron_golem/iron_golem.png");

    public IronGolemCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IronGolemCompanionModel<>(ctx.getPart(IronGolemCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
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
