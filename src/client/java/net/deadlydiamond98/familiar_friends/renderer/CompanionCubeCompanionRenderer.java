package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.OneUpMushroomCompanion;
import net.deadlydiamond98.familiar_friends.models.CompanionCubeModel;
import net.deadlydiamond98.familiar_friends.models.OneUpMushroomModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CompanionCubeCompanionRenderer extends CompanionRenderer<CompanionCubeCompanion, CompanionCubeModel<CompanionCubeCompanion>> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/companion_cube.png");

    public CompanionCubeCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CompanionCubeModel<>(ctx.getPart(CompanionCubeModel.LAYER_LOCATION)));
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
    public Identifier getTexture(CompanionCubeCompanion entity) {
        return TEXTURE;
    }
}
