package net.deadlydiamond98.familiar_friends.entities.renderer.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.CirnoCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.CirnoCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CirnoCompanionRenderer extends CompanionRenderer<CirnoCompanion, CirnoCompanionModel<CirnoCompanion>> {

    private static final Identifier TEXTUREA = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/cirno.png");
    private static final Identifier TEXTUREB = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/cirno_blink.png");
    private Identifier currentTexture;

    public CirnoCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CirnoCompanionModel<>(ctx.getPart(CirnoCompanionModel.LAYER_LOCATION)));
        currentTexture = TEXTUREA;
    }

    @Override
    public void render(CirnoCompanion entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, i);
        currentTexture = TEXTUREA;
        if (entity.age % 120 == 1) {
            currentTexture = TEXTUREB;
        }
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
        return currentTexture;
    }
}
