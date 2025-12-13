package net.deadlydiamond98.familiar_friends.client.rendering.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.TinyTaterCompanion;
import net.deadlydiamond98.familiar_friends.client.models.TinyTaterModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TinyTaterCompanionRenderer extends CompanionRenderer<TinyTaterCompanion, TinyTaterModel<TinyTaterCompanion>> {
    public TinyTaterCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TinyTaterModel<>(ctx.getPart(TinyTaterModel.LAYER_LOCATION)));
    }
    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/tater.png");


    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.0f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }

    @Override
    public Identifier getTexture(TinyTaterCompanion entity) {
        return TEXTURE;
    }
}
