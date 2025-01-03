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

public class BiterCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "biter_companion"), "main");

	private final ModelPart root;
	private final ModelPart leftarm;
	private final ModelPart rightarm;
	private final ModelPart head;
	private final ModelPart leftleg;
	private final ModelPart rightleg;
	private final ModelPart rightear;
	private final ModelPart rightwing;
	private final ModelPart leftear;
	private final ModelPart leftwing;

	public BiterCompanionModel(ModelPart root) {
		this.root = root.getChild("root");
		this.leftarm = this.root.getChild("leftarm");
		this.rightarm = this.root.getChild("rightarm");
		this.head = this.root.getChild("head");
		this.leftleg = this.head.getChild("leftleg");
		this.rightleg = this.head.getChild("rightleg");
		this.rightear = this.head.getChild("rightear");
		this.rightwing = this.rightear.getChild("rightwing");
		this.leftear = this.head.getChild("leftear");
		this.leftwing = this.leftear.getChild("leftwing");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 31.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.02F)), ModelTransform.of(0.0F, -17.0866F, 0.4078F, 0.0873F, 0.0F, 0.0F));

		ModelPartData leftleg = head.addChild("leftleg", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.0F, -1.0F));

		ModelPartData leftleg_r1 = leftleg.addChild("leftleg_r1", ModelPartBuilder.create().uv(24, 1).mirrored().cuboid(-2.0F, 5.0F, -1.0F, 5.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
				.uv(31, 2).mirrored().cuboid(-1.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData rightleg = head.addChild("rightleg", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 0.0F, -1.0F));

		ModelPartData rightleg_r1 = rightleg.addChild("rightleg_r1", ModelPartBuilder.create().uv(24, 1).cuboid(-4.0F, 5.0F, -1.0F, 5.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(31, 2).cuboid(-4.0F, 0.0F, 0.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData rightear = head.addChild("rightear", ModelPartBuilder.create(), ModelTransform.of(-4.0F, -4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData rightwing = rightear.addChild("rightwing", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, -5.0F, 0.0F, 0.0F, 13.0F, 19.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

		ModelPartData leftear = head.addChild("leftear", ModelPartBuilder.create(), ModelTransform.of(4.0F, -4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData leftwing = leftear.addChild("leftwing", ModelPartBuilder.create().uv(26, 32).cuboid(0.0F, -5.0F, 0.0F, 0.0F, 13.0F, 19.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.5672F, 0.0F));

		ModelPartData leftarm = root.addChild("leftarm", ModelPartBuilder.create().uv(0, 37).cuboid(-1.0F, -0.8311F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(8, 37).cuboid(-1.0F, -0.8311F, 1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -18.6689F, -2.2053F, -1.5708F, 0.0F, 0.0873F));

		ModelPartData rightarm = root.addChild("rightarm", ModelPartBuilder.create().uv(0, 37).mirrored().cuboid(-1.0F, -0.8311F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(8, 37).mirrored().cuboid(-1.0F, -0.8311F, 1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, -18.6689F, -2.2053F, -1.5708F, 0.0F, -0.0873F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.rightwing.yaw = -(MathHelper.cos(ageInTicks * 74 * 0.005f) * 0.15f * (float) Math.PI) - 0.55f;
		this.leftwing.yaw = -this.rightwing.yaw;

		this.rightleg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbSwingAmount;
		this.leftleg.pitch = MathHelper.cos(limbSwing * 0.6662f +  (float) Math.PI) * 1.4f * limbSwingAmount;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}