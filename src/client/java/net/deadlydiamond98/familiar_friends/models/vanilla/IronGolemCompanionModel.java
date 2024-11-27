package net.deadlydiamond98.familiar_friends.models.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class IronGolemCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "iron_golem_companion"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public IronGolemCompanionModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F).uv(24, 0).cuboid(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, -7.0F, -2.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 40).cuboid(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F).uv(0, 70).cuboid(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -7.0F, 0.0F));
        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(60, 21).cuboid(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), ModelTransform.pivot(0.0F, -7.0F, 0.0F));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(60, 58).cuboid(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), ModelTransform.pivot(0.0F, -7.0F, 0.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(37, 0).cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), ModelTransform.pivot(-4.0F, 11.0F, 0.0F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(60, 0).mirrored().cuboid(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), ModelTransform.pivot(5.0F, 11.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    public ModelPart getPart() {
        return this.root;
    }

    public void setAngles(T ironGolemEntity, float f, float g, float h, float i, float j) {
        this.head.yaw = i * 0.017453292F;
        this.head.pitch = j * 0.017453292F;
        this.rightLeg.pitch = -1.5F * MathHelper.wrap(f, 13.0F) * g;
        this.leftLeg.pitch = 1.5F * MathHelper.wrap(f, 13.0F) * g;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;
    }

    public void animateModel(T ironGolemEntity, float f, float g, float h) {
        this.rightArm.pitch = (-0.2F + 1.5F * MathHelper.wrap(f, 13.0F)) * g;
        this.leftArm.pitch = (-0.2F - 1.5F * MathHelper.wrap(f, 13.0F)) * g;
    }

    public ModelPart getRightArm() {
        return this.rightArm;
    }
}

