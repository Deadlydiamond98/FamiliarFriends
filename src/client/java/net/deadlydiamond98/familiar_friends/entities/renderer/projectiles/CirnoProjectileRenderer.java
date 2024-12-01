package net.deadlydiamond98.familiar_friends.entities.renderer.projectiles;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class CirnoProjectileRenderer<T extends Entity> extends EntityRenderer<T> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/cirno_projectile.png");

    public CirnoProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, i);
        matrices.push();

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(camera.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));
        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f modelMatrix = matrixEntry.getPositionMatrix();

        int light = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.25F, 0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrixEntry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, 0.25F, 0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrixEntry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, 0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 0.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrixEntry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 0.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrixEntry, 0, 1, 0);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return TEXTURE;
    }
}
