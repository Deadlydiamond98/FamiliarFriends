package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.NotchCompanion;
import net.deadlydiamond98.familiar_friends.models.vanilla.BipedCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class NotchCompanionRenderer extends CompanionRenderer<NotchCompanion, BipedCompanionModel<NotchCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/notch.png");

    public NotchCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedCompanionModel<>(ctx.getPart(BipedCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(NotchCompanion entity) {
        return TEXTURE;
    }
}
