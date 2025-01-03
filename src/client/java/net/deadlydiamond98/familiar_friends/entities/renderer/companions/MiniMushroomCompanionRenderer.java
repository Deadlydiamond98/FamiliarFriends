package net.deadlydiamond98.familiar_friends.entities.renderer.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.MiniMushroomCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.OneUpMushroomModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MiniMushroomCompanionRenderer extends CompanionRenderer<MiniMushroomCompanion, OneUpMushroomModel<MiniMushroomCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/mini_mushroom.png");

    public MiniMushroomCompanionRenderer(EntityRendererFactory.Context ctx) {
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
    public Identifier getTexture(MiniMushroomCompanion entity) {
        return TEXTURE;
    }
}
