package net.deadlydiamond98.familiar_friends.models.vanilla;

import com.google.common.collect.ImmutableList;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class OcelotCompanionModel<T extends Entity> extends AnimalModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "ocelot_companion"), "main");

    private static final int SNEAKING_ANIMATION_STATE = 0;
    private static final int STANDING_ANIMATION_STATE = 1;
    private static final int SPRINTING_ANIMATION_STATE = 2;
    protected static final int SITTING_ANIMATION_STATE = 3;
    private static final float field_32527 = 0.0F;
    private static final float BODY_SIZE_Y = 16.0F;
    private static final float field_32529 = -9.0F;
    private static final float HEAD_PIVOT_Y = 15.0F;
    private static final float HEAD_PIVOT_Z = -9.0F;
    private static final float BODY_PIVOT_Y = 12.0F;
    private static final float BODY_PIVOT_Z = -10.0F;
    private static final float UPPER_TAIL_PIVOT_Y = 15.0F;
    private static final float UPPER_TAIL_PIVOT_Z = 8.0F;
    private static final float LOWER_TAIL_PIVOT_Y = 20.0F;
    private static final float LOWER_TAIL_PIVOT_Z = 14.0F;
    protected static final float HIND_LEG_PIVOT_Y = 18.0F;
    protected static final float HIND_LEG_PIVOT_Z = 5.0F;
    protected static final float FRONT_LEG_PIVOT_Y = 14.1F;
    private static final float FRONT_LEG_PIVOT_Z = -5.0F;
    private static final String TAIL1 = "tail1";
    private static final String TAIL2 = "tail2";
    protected final ModelPart leftHindLeg;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftFrontLeg;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart upperTail;
    protected final ModelPart lowerTail;
    protected final ModelPart head;
    protected final ModelPart body;
    protected int animationState = 1;

    public OcelotCompanionModel(ModelPart root) {
        super(true, 10.0F, 4.0F);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.upperTail = root.getChild("tail1");
        this.lowerTail = root.getChild("tail2");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
    }

    public static TexturedModelData getTexturedModelData() {

        Dilation dilation = new Dilation(0.0f);

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation2 = new Dilation(-0.02F);
        modelPartData.addChild("head", ModelPartBuilder.create().cuboid("main", -2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, dilation).cuboid("nose", -1.5F, -0.001F, -4.0F, 3, 2, 2, dilation, 0, 24).cuboid("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, dilation, 0, 10).cuboid("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, dilation, 6, 10), ModelTransform.pivot(0.0F, 15.0F, -9.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(20, 0).cuboid(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, dilation), ModelTransform.of(0.0F, 12.0F, -10.0F, 1.5707964F, 0.0F, 0.0F));
        modelPartData.addChild("tail1", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, dilation), ModelTransform.of(0.0F, 15.0F, 8.0F, 0.9F, 0.0F, 0.0F));
        modelPartData.addChild("tail2", ModelPartBuilder.create().uv(4, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, dilation2), ModelTransform.pivot(0.0F, 20.0F, 14.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, dilation);
        modelPartData.addChild("left_hind_leg", modelPartBuilder, ModelTransform.pivot(1.1F, 18.0F, 5.0F));
        modelPartData.addChild("right_hind_leg", modelPartBuilder, ModelTransform.pivot(-1.1F, 18.0F, 5.0F));
        ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, dilation);
        modelPartData.addChild("left_front_leg", modelPartBuilder2, ModelTransform.pivot(1.2F, 14.1F, -5.0F));
        modelPartData.addChild("right_front_leg", modelPartBuilder2, ModelTransform.pivot(-1.2F, 14.1F, -5.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.leftHindLeg, this.rightHindLeg, this.leftFrontLeg, this.rightFrontLeg, this.upperTail, this.lowerTail, this.head);
    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        if (this.animationState != 3) {
            this.body.pitch = 1.5707964F;
            if (this.animationState == 2) {
                this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
                this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 0.3F) * limbDistance;
                this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F + 0.3F) * limbDistance;
                this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
                this.lowerTail.pitch = 1.7278761F + 0.31415927F * MathHelper.cos(limbAngle) * limbDistance;
            } else {
                this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
                this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
                this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
                this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
                if (this.animationState == 1) {
                    this.lowerTail.pitch = 1.7278761F + 0.7853982F * MathHelper.cos(limbAngle) * limbDistance;
                } else {
                    this.lowerTail.pitch = 1.7278761F + 0.47123894F * MathHelper.cos(limbAngle) * limbDistance;
                }
            }
        }

    }

    public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
        this.body.pivotY = 12.0F;
        this.body.pivotZ = -10.0F;
        this.head.pivotY = 15.0F;
        this.head.pivotZ = -9.0F;
        this.upperTail.pivotY = 15.0F;
        this.upperTail.pivotZ = 8.0F;
        this.lowerTail.pivotY = 20.0F;
        this.lowerTail.pivotZ = 14.0F;
        this.leftFrontLeg.pivotY = 14.1F;
        this.leftFrontLeg.pivotZ = -5.0F;
        this.rightFrontLeg.pivotY = 14.1F;
        this.rightFrontLeg.pivotZ = -5.0F;
        this.leftHindLeg.pivotY = 18.0F;
        this.leftHindLeg.pivotZ = 5.0F;
        this.rightHindLeg.pivotY = 18.0F;
        this.rightHindLeg.pivotZ = 5.0F;
        this.upperTail.pitch = 0.9F;
        ModelPart var10000;
        if (entity.isInSneakingPose()) {
            ++this.body.pivotY;
            var10000 = this.head;
            var10000.pivotY += 2.0F;
            ++this.upperTail.pivotY;
            var10000 = this.lowerTail;
            var10000.pivotY += -4.0F;
            var10000 = this.lowerTail;
            var10000.pivotZ += 2.0F;
            this.upperTail.pitch = 1.5707964F;
            this.lowerTail.pitch = 1.5707964F;
            this.animationState = 0;
        } else if (entity.isSprinting()) {
            this.lowerTail.pivotY = this.upperTail.pivotY;
            var10000 = this.lowerTail;
            var10000.pivotZ += 2.0F;
            this.upperTail.pitch = 1.5707964F;
            this.lowerTail.pitch = 1.5707964F;
            this.animationState = 2;
        } else {
            this.animationState = 1;
        }

    }
}