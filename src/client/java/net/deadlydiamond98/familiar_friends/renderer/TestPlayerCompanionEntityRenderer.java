package net.deadlydiamond98.familiar_friends.renderer;

import com.google.common.collect.Lists;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.models.TestPlayerCompanionModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.List;

public class TestPlayerCompanionEntityRenderer<T extends PlayerCompanion> extends EntityRenderer<T> {
    private static final Identifier TEXTURE = Identifier.of("textures/entity/bat.png");

    protected TestPlayerCompanionModel entityModel;
    public TestPlayerCompanionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new TestPlayerCompanionModel<>(ctx.getPart(TestPlayerCompanionModel.LAYER_LOCATION));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(0.0D, 1.5D, 0.0D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - yaw));
        matrices.scale(-1.0F, -1.0F, 1.0F);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(this.getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity)  {
        return TEXTURE;
    }
}