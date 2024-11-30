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

public class RanaCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "rana_companion"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	public RanaCompanionModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("Head");
		this.body = root.getChild("Body");
		this.rightArm = root.getChild("RightArm");
		this.leftArm = root.getChild("LeftArm");
		this.rightLeg = root.getChild("RightLeg");
		this.leftLeg = root.getChild("LeftLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create().uv(0, 2).cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData frogehat = Head.addChild("frogehat", ModelPartBuilder.create().uv(0, 77).cuboid(1.0F, -6.3F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.01F))
		.uv(28, 69).cuboid(-4.0F, -6.3F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.01F))
		.uv(37, 67).cuboid(-4.0F, -4.3F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.01F))
		.uv(1, 65).cuboid(-4.5F, -1.5F, -4.5F, 9.0F, 2.0F, 9.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, -5.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData hair = Head.addChild("hair", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData pigtail1 = hair.addChild("pigtail1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = pigtail1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 82).cuboid(-1.8F, -1.5F, 0.5F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(53, 70).cuboid(-1.3F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 4.0F, -0.7657F, 0.4127F, -0.0643F));

		ModelPartData pigtail2 = hair.addChild("pigtail2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r2 = pigtail2.addChild("cube_r2", ModelPartBuilder.create().uv(0, 82).mirrored().cuboid(-1.2F, -1.5F, 0.5F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(53, 70).mirrored().cuboid(-0.7F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.0F, 0.0F, 4.0F, -0.7657F, -0.4127F, 0.0643F));

		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.25F))
		.uv(46, 0).cuboid(-3.0F, 2.0F, 2.0F, 6.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightArm = modelPartData.addChild("RightArm", ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData LeftArm = modelPartData.addChild("LeftArm", ModelPartBuilder.create().uv(32, 48).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData RightLeg = modelPartData.addChild("RightLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

		ModelPartData LeftLeg = modelPartData.addChild("LeftLeg", ModelPartBuilder.create().uv(16, 48).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 48).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yaw = netHeadYaw * 0.017453292F;
		this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
		this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}