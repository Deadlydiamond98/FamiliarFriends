package net.deadlydiamond98.familiar_friends.entities.models.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Identifier;

public class GoatCompanionModel<T extends PlayerCompanion> extends QuadrupedEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "goat_companion"), "main");

    public GoatCompanionModel(ModelPart root) {
        super(root, true, 19.0F, 1.0F, 2.5F, 2.0F, 24);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("head", ModelPartBuilder.create().uv(2, 61).cuboid("right ear", -6.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F).uv(2, 61).mirrored().cuboid("left ear", 2.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F).uv(23, 52).cuboid("goatee", -0.5F, -3.0F, -14.0F, 0.0F, 7.0F, 5.0F), ModelTransform.pivot(1.0F, 14.0F, 0.0F));
        modelPartData2.addChild("left_horn", ModelPartBuilder.create().uv(12, 55).cuboid(-0.01F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData2.addChild("right_horn", ModelPartBuilder.create().uv(12, 55).cuboid(-2.99F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData2.addChild("nose", ModelPartBuilder.create().uv(34, 46).cuboid(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F), ModelTransform.of(0.0F, -8.0F, -8.0F, 0.9599F, 0.0F, 0.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(1, 1).cuboid(-4.0F, -17.0F, -7.0F, 9.0F, 11.0F, 16.0F).uv(0, 28).cuboid(-5.0F, -18.0F, -8.0F, 11.0F, 14.0F, 11.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(36, 29).cuboid(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(1.0F, 14.0F, 4.0F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(49, 29).cuboid(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-3.0F, 14.0F, 4.0F));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(49, 2).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), ModelTransform.pivot(1.0F, 14.0F, -6.0F));
        modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(35, 2).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), ModelTransform.pivot(-3.0F, 14.0F, -6.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(T goatEntity, float f, float g, float h, float i, float j) {
        super.setAngles(goatEntity, f, g, h, i, j);
    }
}