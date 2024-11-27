package net.deadlydiamond98.familiar_friends.renderer.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.SkeletonCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.models.CompanionCubeModel;
import net.deadlydiamond98.familiar_friends.models.NaviEntityModel;
import net.deadlydiamond98.familiar_friends.renderer.CompanionRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SkeletonCompanionRenderer extends CompanionRenderer<SkeletonCompanion, SkeletonCompanionModel<SkeletonCompanion>> {

    private static final Identifier TEXTURE = Identifier.of("textures/entity/skeleton/skeleton.png");

    public SkeletonCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SkeletonCompanionModel<>(ctx.getPart(SkeletonCompanionModel.LAYER_LOCATION)));
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.75f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }


    @Override
    public Identifier getTexture(SkeletonCompanion entity) {
        return TEXTURE;
    }
}
