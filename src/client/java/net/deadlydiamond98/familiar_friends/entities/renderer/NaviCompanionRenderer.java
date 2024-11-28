package net.deadlydiamond98.familiar_friends.entities.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
import net.deadlydiamond98.familiar_friends.entities.models.NaviEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class NaviCompanionRenderer extends CompanionRenderer<NaviCompanion, NaviEntityModel<NaviCompanion>> {
    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/blue_fairy.png");

    public NaviCompanionRenderer(EntityRendererFactory.Context context) {
           super(context, new NaviEntityModel<>(context.getPart(NaviEntityModel.LAYER_LOCATION)));
    }

    public Identifier getTexture(NaviCompanion fairy) {
            return TEXTURE;
    }

    @Override
    protected void otherModelParts(NaviCompanion entity, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, float tickDelta) {
        super.otherModelParts(entity, matrices, vertexConsumerProvider, tickDelta);
        renderBody(entity, matrices, vertexConsumerProvider, tickDelta);
    }

    @Override
    protected void guiScale(MatrixStack matrices) {
        this.scale(matrices, 1.0f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.25f);
    }

    private void renderBody(NaviCompanion fairy, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, float g) {
        matrixStack.push();

        float size = fairy.isBookRender() ? 2 : 0.5f;

        matrixStack.translate(0.0f, 0.3125f * size, 0.0f);

        if (!fairy.isBookRender()) {
            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(camera.getYaw()));
            matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(360.0f - MathHelper.clamp(camera.getPitch(), -80, 80)));
        }
        else {
            float rotationAngle = (float)(System.currentTimeMillis() % 10000l) / 10000.0f * 360.0f;
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationAngle));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0.0f));
        }


        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
        float minUV = 0.0f;
        float maxUV = 0.375f;

        int light = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.15f * size,  0.15f * size, 0.01f).color(255, 255, 255, 255).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.15f * size,  0.15f * size, 0.01f).color(255, 255, 255, 255).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.15f * size, -0.15f * size, 0.01f).color(255, 255, 255, 255).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.15f * size, -0.15f * size, 0.01f).color(255, 255, 255, 255).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        vertexConsumer.vertex(modelMatrix, -0.17f * size,  0.17f * size, 0.0f).color(255, 255, 255, 50).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.17f * size,  0.17f * size, 0.0f).color(255, 255, 255, 50).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.17f * size, -0.17f * size, 0.0f).color(255, 255, 255, 50).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.17f * size, -0.17f * size, 0.0f).color(255, 255, 255, 50).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        vertexConsumer.vertex(modelMatrix, -0.18f * size,  0.18f * size, 0.0f).color(255, 255, 255, 10).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.18f * size,  0.18f * size, 0.0f).color(255, 255, 255, 10).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.18f * size, -0.18f * size, 0.0f).color(255, 255, 255, 10).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.18f * size, -0.18f * size, 0.0f).color(255, 255, 255, 10).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        matrixStack.pop();
    }
}