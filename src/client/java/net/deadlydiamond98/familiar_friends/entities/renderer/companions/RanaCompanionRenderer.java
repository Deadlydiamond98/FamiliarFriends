package net.deadlydiamond98.familiar_friends.entities.renderer.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.RanaCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.RanaCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.renderer.CompanionRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RanaCompanionRenderer extends CompanionRenderer<RanaCompanion, RanaCompanionModel<RanaCompanion>> {

    private static final Identifier TEXTUREA = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/rana.png");
    private static final Identifier TEXTUREB = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/rana_blink.png");

    private Identifier currentTexture;

    public RanaCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RanaCompanionModel<>(ctx.getPart(RanaCompanionModel.LAYER_LOCATION)));
        currentTexture = TEXTUREA;
    }

    @Override
    public void render(RanaCompanion entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, i);
        currentTexture = TEXTUREA;
        if (entity.age % 120 == 1) {
            currentTexture = TEXTUREB;
        }
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 0.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.125f);
    }


    @Override
    public Identifier getTexture(RanaCompanion entity) {
        return currentTexture;
    }
}
