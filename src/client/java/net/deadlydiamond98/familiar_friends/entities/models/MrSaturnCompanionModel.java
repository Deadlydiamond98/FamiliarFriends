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

public class MrSaturnCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "mr_saturn_companion"), "main");

	private final ModelPart body;
	private final ModelPart leftFoot;
	private final ModelPart rightFoot;
	public MrSaturnCompanionModel(ModelPart root) {
		this.body = root.getChild("body");
		this.rightFoot = body.getChild("rightFoot");
		this.leftFoot = body.getChild("leftFoot");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(4.0F, 0.0F, -5.15F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 0).mirrored().cuboid(-9.0F, 0.0F, -5.15F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 20).cuboid(-2.5F, -0.5F, -10.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 17.0F, 0.0F));

		ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(20, 27).cuboid(-2.5F, -8.0F, 0.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData leftFoot = body.addChild("leftFoot", ModelPartBuilder.create().uv(20, 20).cuboid(-5.75F, 5.0F, -4.5F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		ModelPartData rightFoot = body.addChild("rightFoot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r2 = rightFoot.addChild("cube_r2", ModelPartBuilder.create().uv(20, 20).mirrored().cuboid(2.75F, 5.0F, -4.5F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.leftFoot.pitch = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.rightFoot.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
	}

	@Override
	public ModelPart getPart() {
		return this.body;
	}
}