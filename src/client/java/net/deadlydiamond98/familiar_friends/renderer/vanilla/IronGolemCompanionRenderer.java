package net.deadlydiamond98.familiar_friends.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.IronGolemCompanion;
import net.deadlydiamond98.familiar_friends.models.vanilla.IronGolemCompanionModel;
import net.deadlydiamond98.familiar_friends.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class IronGolemCompanionRenderer extends CompanionRenderer<IronGolemCompanion, IronGolemCompanionModel<IronGolemCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/iron_golem/iron_golem.png");

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
