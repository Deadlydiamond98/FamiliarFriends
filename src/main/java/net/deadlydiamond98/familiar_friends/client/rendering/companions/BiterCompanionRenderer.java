package net.deadlydiamond98.familiar_friends.client.rendering.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.BiterCompanion;
import net.deadlydiamond98.familiar_friends.client.models.BiterCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BiterCompanionRenderer extends CompanionRenderer<BiterCompanion, BiterCompanionModel<BiterCompanion>> {

    private static final Identifier TEXTURE = new Identifier(FamiliarFriends.MOD_ID, "textures/entity/biter.png");

    public BiterCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BiterCompanionModel<>(ctx.getPart(BiterCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 0.87f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.33f);
    }


    @Override
    public Identifier getTexture(BiterCompanion entity) {
        return TEXTURE;
    }
}
