
package net.deadlydiamond98.familiar_friends.models;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class NaviEntityModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "fairy_companion"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	public NaviEntityModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.left_wing = body.getChild("left_wing");
		this.right_wing = body.getChild("right_wing");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.0F, 3.0F));

		ModelPartData cube_r1 = right_wing.addChild("cube_r1", ModelPartBuilder.create().uv(0, 6).mirrored().cuboid(0.0F, -5.0F, -1.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0F, -1.0F, 1.0F, 0.2444F, 0.7703F, 0.1719F));

		ModelPartData cube_r2 = right_wing.addChild("cube_r2", ModelPartBuilder.create().uv(2, 16).mirrored().cuboid(0.0F, -1.0F, -1.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0F, 1.0F, 1.0F, 0.5051F, 0.5086F, 0.263F));

		ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.0F, 3.0F));

		ModelPartData cube_r3 = left_wing.addChild("cube_r3", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -5.0F, -1.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -1.0F, 1.0F, 0.2444F, -0.7703F, -0.1719F));

		ModelPartData cube_r4 = left_wing.addChild("cube_r4", ModelPartBuilder.create().uv(2, 16).cuboid(0.0F, -1.0F, -1.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 1.0F, 0.5051F, -0.5086F, -0.263F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.right_wing.yaw = MathHelper.cos(ageInTicks * 74 * 0.005f) * 0.25f * (float) Math.PI;
		this.left_wing.yaw = -this.right_wing.yaw;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}