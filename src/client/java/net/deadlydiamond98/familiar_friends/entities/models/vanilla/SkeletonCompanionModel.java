package net.deadlydiamond98.familiar_friends.entities.models.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class SkeletonCompanionModel<T extends PlayerCompanion> extends BipedCompanionModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "skeleton_companion"), "main");

    public SkeletonCompanionModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        addLimbs(modelPartData);
        return TexturedModelData.of(modelData, 64, 32);
    }

    protected static void addLimbs(ModelPartData data) {
        data.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        data.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        data.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        data.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
    }

    public void animateModel(T mobEntity, float f, float g, float h) {
        super.animateModel(mobEntity, f, g, h);
    }

    public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
        super.setAngles(mobEntity, f, g, h, i, j);

        this.rightArm.pitch = 80;
        this.leftArm.pitch = this.rightArm.pitch;
    }

    @Override
    protected ModelPart getArm(Arm arm) {
        return super.getArm(arm);
    }
}