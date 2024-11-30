package net.deadlydiamond98.familiar_friends.entities.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.CirnoCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.CirnoCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.models.CompanionCubeModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CirnoCompanionRenderer extends CompanionRenderer<CirnoCompanion, CirnoCompanionModel<CirnoCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/cirno.png");

    public CirnoCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CirnoCompanionModel<>(ctx.getPart(CirnoCompanionModel.LAYER_LOCATION)));
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
    public Identifier getTexture(CirnoCompanion entity) {
        return TEXTURE;
    }
}
