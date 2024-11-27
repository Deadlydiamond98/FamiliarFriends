package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.TaterCompanion;
import net.deadlydiamond98.familiar_friends.models.CompanionCubeModel;
import net.deadlydiamond98.familiar_friends.models.LilTaterCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TaterCompanionRenderer extends CompanionRenderer<TaterCompanion, LilTaterCompanionModel<TaterCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/lil_tater.png");

    public TaterCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LilTaterCompanionModel<>(ctx.getPart(LilTaterCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(TaterCompanion entity) {
        return TEXTURE;
    }
}
