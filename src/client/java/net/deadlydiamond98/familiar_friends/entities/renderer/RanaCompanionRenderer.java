package net.deadlydiamond98.familiar_friends.entities.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.CirnoCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.RanaCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.CirnoCompanionModel;
import net.deadlydiamond98.familiar_friends.entities.models.RanaCompanionModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RanaCompanionRenderer extends CompanionRenderer<RanaCompanion, RanaCompanionModel<RanaCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/rana.png");

    public RanaCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RanaCompanionModel<>(ctx.getPart(RanaCompanionModel.LAYER_LOCATION)));
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
        return TEXTURE;
    }
}
