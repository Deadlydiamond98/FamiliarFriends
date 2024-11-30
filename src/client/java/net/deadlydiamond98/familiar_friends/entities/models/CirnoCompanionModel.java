// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.familiar_friends.entities.models;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CirnoCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "cirno_companion"), "main");
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart head;
	public CirnoCompanionModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leftArm = body.getChild("leftArm");
		this.rightArm = body.getChild("rightArm");
		ModelPart wings = body.getChild("wings");
		this.leftWing = wings.getChild("leftWing");
		this.rightWing = wings.getChild("rightWing");
		this.head = body.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-3.0F, -0.75F, -2.0F, 6.0F, 6.0F, 4.0F, new Dilation(-0.75F))
				.uv(0, 31).cuboid(-2.5F, -5.6F, -1.5F, 5.0F, 6.0F, 3.0F, new Dilation(-0.4F)), ModelTransform.of(0.0F, 15.5F, 0.0F, 0.1309F, 0.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(20, 7).mirrored().cuboid(-1.85F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)).mirrored(false), ModelTransform.pivot(-2.0F, -4.5F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(24, 15).cuboid(-0.15F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.pivot(2.0F, -4.5F, 0.0F));

		ModelPartData wings = body.addChild("wings", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -0.5F));

		ModelPartData leftWing = wings.addChild("leftWing", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		ModelPartData cube_r1 = leftWing.addChild("cube_r1", ModelPartBuilder.create().uv(26, 35).cuboid(-2.5F, -11.0F, 1.0F, 5.0F, 19.0F, 10.0F, new Dilation(-2.5F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.6545F, 0.0F));

		ModelPartData rightWing = wings.addChild("rightWing", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

		ModelPartData cube_r2 = rightWing.addChild("cube_r2", ModelPartBuilder.create().uv(26, 35).mirrored().cuboid(-2.5F, -11.0F, 1.0F, 5.0F, 19.0F, 10.0F, new Dilation(-2.5F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.6545F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
				.uv(40, 0).cuboid(-3.0F, -5.5F, -2.9F, 6.0F, 6.0F, 6.0F, new Dilation(-0.35F)), ModelTransform.of(0.0F, -5.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData bow = head.addChild("bow", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.85F, 0.4F, -0.0873F, 0.0F, 0.0F));

		ModelPartData cube_r3 = bow.addChild("cube_r3", ModelPartBuilder.create().uv(28, 7).cuboid(-0.25F, -2.0F, -0.75F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 3.0F, 0.2618F, -0.3491F, 0.0F));

		ModelPartData cube_r4 = bow.addChild("cube_r4", ModelPartBuilder.create().uv(24, 23).cuboid(-3.75F, -2.0F, -0.75F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 3.0F, 0.2618F, 0.3491F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yaw = netHeadYaw * 0.017453292F;
		this.rightWing.yaw = MathHelper.cos(ageInTicks * 74 * 0.005f) * 0.15f * (float) Math.PI;
		this.leftWing.yaw = -this.rightWing.yaw;
		this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.5f;
		this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount * 0.5f;
	}

	@Override
	public ModelPart getPart() {
		return this.body;
	}
}