package net.deadlydiamond98.familiar_friends.entities.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.GoatCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.GoatCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GoatCompanionRenderer extends CompanionRenderer<GoatCompanion, GoatCompanionModel<GoatCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/goat/goat.png");

    public GoatCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GoatCompanionModel<>(ctx.getPart(GoatCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(GoatCompanion entity) {
        return TEXTURE;
    }
}
