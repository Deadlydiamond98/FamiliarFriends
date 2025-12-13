package net.deadlydiamond98.familiar_friends.client.rendering.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.rendering.CompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.OneUpMushroomCompanion;
import net.deadlydiamond98.familiar_friends.client.models.OneUpMushroomModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OneUpMushroomCompanionRenderer extends CompanionRenderer<OneUpMushroomCompanion, OneUpMushroomModel<OneUpMushroomCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/one_up_mushroom.png");

    public OneUpMushroomCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OneUpMushroomModel<>(ctx.getPart(OneUpMushroomModel.LAYER_LOCATION)));
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
    public Identifier getTexture(OneUpMushroomCompanion entity) {
        return TEXTURE;
    }
}
