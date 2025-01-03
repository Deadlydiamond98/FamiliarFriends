package net.deadlydiamond98.familiar_friends.entities.renderer.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.LemonCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.LemonCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LemonCompanionRenderer extends CompanionRenderer<LemonCompanion, LemonCompanionModel<LemonCompanion>> {

    private static final Identifier TEXTURE = new Identifier(FamiliarFriends.MOD_ID, "textures/entity/lemon_4k.png");

    public LemonCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LemonCompanionModel<>(ctx.getPart(LemonCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.25f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }


    @Override
    public Identifier getTexture(LemonCompanion entity) {
        return TEXTURE;
    }
}
