
package net.deadlydiamond98.familiar_friends.models;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CompanionCubeModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "companion_cube_companion"), "main");

	private final ModelPart root;
	private final ModelPart cube;

	public CompanionCubeModel(ModelPart root) {
		this.root = root;
		this.cube = root.getChild("cube");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData cube = modelPartData.addChild("cube", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5F, -13.5F, -6.5F, 13.0F, 13.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData corners = cube.addChild("corners", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, -4.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 26).cuboid(-7.0F, -4.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = corners.addChild("cube_r1", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r2 = corners.addChild("cube_r2", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r3 = corners.addChild("cube_r3", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 3.1416F, 0.0F, -1.5708F));

		ModelPartData cube_r4 = corners.addChild("cube_r4", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 1.5708F, 0.0F, -1.5708F));

		ModelPartData cube_r5 = corners.addChild("cube_r5", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, -1.5708F, 0.0F, -1.5708F));

		ModelPartData cube_r6 = corners.addChild("cube_r6", ModelPartBuilder.create().uv(0, 26).mirrored().cuboid(3.0F, 3.0F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData edges = cube.addChild("edges", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 5.0F));

		ModelPartData cube_r7 = edges.addChild("cube_r7", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r8 = edges.addChild("cube_r8", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData cube_r9 = edges.addChild("cube_r9", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r10 = edges.addChild("cube_r10", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, -3.1416F, 0.0F, -3.1416F));

		ModelPartData cube_r11 = edges.addChild("cube_r11", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, -1.5708F, 1.5708F));

		ModelPartData cube_r12 = edges.addChild("cube_r12", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r13 = edges.addChild("cube_r13", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, 1.5708F, -1.5708F));

		ModelPartData cube_r14 = edges.addChild("cube_r14", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, -1.5708F, 0.0F, -3.1416F));

		ModelPartData cube_r15 = edges.addChild("cube_r15", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 1.5708F, 0.0F, -1.5708F));

		ModelPartData cube_r16 = edges.addChild("cube_r16", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r17 = edges.addChild("cube_r17", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, 3.1416F, 0.0F, -1.5708F));

		ModelPartData cube_r18 = edges.addChild("cube_r18", ModelPartBuilder.create().uv(23, 39).mirrored().cuboid(4.0F, -1.5F, -7.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.0F, -5.0F, -1.5708F, 0.0F, -1.5708F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}