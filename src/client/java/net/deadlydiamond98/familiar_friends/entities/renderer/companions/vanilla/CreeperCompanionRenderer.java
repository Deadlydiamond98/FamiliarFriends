package net.deadlydiamond98.familiar_friends.entities.renderer.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SkeletonCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.vanilla.SkeletonCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CreeperCompanionRenderer extends CompanionRenderer<CreeperCompanion, CreeperEntityModel<CreeperCompanion>> {

    private static final Identifier TEXTURE = new Identifier("textures/entity/creeper/creeper.png");

    public CreeperCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CreeperEntityModel<>(ctx.getPart(EntityModelLayers.CREEPER)));
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
    public Identifier getTexture(CreeperCompanion entity) {
        return TEXTURE;
    }
}
