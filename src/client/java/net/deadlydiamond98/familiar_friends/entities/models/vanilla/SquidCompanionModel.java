package net.deadlydiamond98.familiar_friends.entities.models.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class SquidCompanionModel<T extends PlayerCompanion> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(FamiliarFriends.MOD_ID, "squid_companion"), "main");

    private final ModelPart[] tentacles = new ModelPart[8];
    private final ModelPart root;

    public SquidCompanionModel(ModelPart root) {
        this.root = root;
        Arrays.setAll(this.tentacles, (index) -> {
            return root.getChild(getTentacleName(index));
        });
    }

    private static String getTentacleName(int index) {
        return "tentacle" + index;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.02F);
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F, dilation), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(48, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);

        for(int k = 0; k < 8; ++k) {
            double d = (double)k * Math.PI * 2.0 / 8.0;
            float f = (float)Math.cos(d) * 5.0F;
            float g = 15.0F;
            float h = (float)Math.sin(d) * 5.0F;
            d = (double)k * Math.PI * -2.0 / 8.0 + 1.5707963267948966;
            float l = (float)d;
            modelPartData.addChild(getTentacleName(k), modelPartBuilder, ModelTransform.of(f, 15.0F, h, 0.0F, l, 0.0F));
        }

        return TexturedModelData.of(modelData, 64, 32);
    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.root.pivotY = -6;
        ModelPart[] var7 = this.tentacles;
        int var8 = var7.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            ModelPart modelPart = var7[var9];
            modelPart.pitch = (MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance) + 0.5f;
        }
    }

    public ModelPart getPart() {
        return this.root;
    }
}
