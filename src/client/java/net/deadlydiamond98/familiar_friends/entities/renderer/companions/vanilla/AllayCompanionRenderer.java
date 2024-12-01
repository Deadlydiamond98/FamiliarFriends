package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.AllayCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.AllayCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.ChickenCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.AllayEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AllayCompanionRenderer extends CompanionRenderer<AllayCompanion, AllayCompanionModel<AllayCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/allay/allay.png");

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
