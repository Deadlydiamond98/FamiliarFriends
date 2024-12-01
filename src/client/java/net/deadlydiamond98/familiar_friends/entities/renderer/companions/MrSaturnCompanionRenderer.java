package net.deadlydiamond98.familiar_friends.entities.renderer.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.MrSaturnCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.MrSaturnCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MrSaturnCompanionRenderer extends CompanionRenderer<MrSaturnCompanion, MrSaturnCompanionModel<MrSaturnCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/mr_saturn.png");

    public MrSaturnCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MrSaturnCompanionModel<>(ctx.getPart(MrSaturnCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(MrSaturnCompanion entity) {
        return TEXTURE;
    }
}
