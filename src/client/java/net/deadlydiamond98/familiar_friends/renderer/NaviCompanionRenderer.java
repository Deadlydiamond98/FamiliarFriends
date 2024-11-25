package net.deadlydiamond98.familiar_friends.renderer;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
import net.deadlydiamond98.familiar_friends.models.NaviEntityModel;
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
    public void render(NaviCompanion mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        renderBody(mobEntity, matrixStack, vertexConsumerProvider);
    }

    @Override
    protected void setupTransforms(NaviCompanion entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {

    }

    @Override
    protected void bookScale(MatrixStack matrices) {
        this.scale(matrices, 1.0f);
    }

    @Override
    protected void worldScale(MatrixStack matrices) {
        this.scale(matrices, 0.5f);
    }

    private void renderBody(NaviCompanion fairy, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        matrixStack.push();

        matrixStack.translate(0.0F, 0.3125F, 0.0F);
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(camera.getYaw()));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(360.0f - MathHelper.clamp(camera.getPitch(), -80, 80)));


        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
        float minUV = 0.0F;
        float maxUV = 0.375F;

        int light = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.15F,  0.15F, 0.01F).color(255, 255, 255, 255).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.15F,  0.15F, 0.01F).color(255, 255, 255, 255).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.15F, -0.15F, 0.01F).color(255, 255, 255, 255).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.15F, -0.15F, 0.01F).color(255, 255, 255, 255).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        vertexConsumer.vertex(modelMatrix, -0.17F,  0.17F, 0.0F).color(255, 255, 255, 50).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.17F,  0.17F, 0.0F).color(255, 255, 255, 50).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.17F, -0.17F, 0.0F).color(255, 255, 255, 50).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.17F, -0.17F, 0.0F).color(255, 255, 255, 50).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        vertexConsumer.vertex(modelMatrix, -0.18F,  0.18F, 0.0F).color(255, 255, 255, 10).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.18F,  0.18F, 0.0F).color(255, 255, 255, 10).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  0.18F, -0.18F, 0.0F).color(255, 255, 255, 10).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.18F, -0.18F, 0.0F).color(255, 255, 255, 10).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);

        matrixStack.pop();
    }
}