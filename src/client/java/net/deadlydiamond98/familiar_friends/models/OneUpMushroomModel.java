
package net.deadlydiamond98.familiar_friends.models;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;

public class OneUpMushroomModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "one_up_mushroom_companion"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart cap;
	private final ModelPart stem;
	public OneUpMushroomModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.cap = body.getChild("cap");
		this.stem = body.getChild("stem");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData cap = body.addChild("cap", ModelPartBuilder.create().uv(24, 0).cuboid(-5.0F, -6.0F, -5.0F, 10.0F, 6.0F, 10.0F, new Dilation(0.0F))
		.uv(2, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData stem = body.addChild("stem", ModelPartBuilder.create().uv(20, 32).cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
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