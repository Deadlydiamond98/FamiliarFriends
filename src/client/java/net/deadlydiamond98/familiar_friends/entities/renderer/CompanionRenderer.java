package net.deadlydiamond98.familiar_friends.entities.renderer;

import com.google.common.collect.Lists;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public abstract class CompanionRenderer<T extends PlayerCompanion, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected M model;

    protected final List<FeatureRenderer<T, M>> features = Lists.newArrayList();
    public CompanionRenderer(EntityRendererFactory.Context ctx, M model) {
        super(ctx);
        this.model = model;
    }

    protected final boolean addFeature(FeatureRenderer<T, M> feature) {
        return this.features.add(feature);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrices.push();

        if (this.doBobbingAnimation() && !entity.isBookRender()) {
            matrices.translate(0, Math.sin(entity.age * 0.1) * 0.1, 0);
        }

        matrices.push();
        this.model.handSwingProgress = this.getHandSwingProgress(entity, tickDelta);

        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, entity.prevBodyYaw, entity.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, entity.prevHeadYaw, entity.headYaw);

        float netHeadYaw = headYaw - bodyYaw;
        netHeadYaw = MathHelper.wrapDegrees(netHeadYaw);

        float animationProgress = this.getAnimationProgress(entity, tickDelta);
        this.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta, 0);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, matrices);
        matrices.translate(0.0F, -1.501F, 0.0F);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.bodyYaw + 180));

        float limbSwing = entity.limbAnimator.getSpeed(tickDelta);
        float limbSwingAmount = entity.limbAnimator.getPos(tickDelta);

        if (limbSwing > 1.0F) {
            limbSwing = 1.0F;
        }

        this.model.animateModel(entity, limbSwingAmount, limbSwing, tickDelta);
        this.model.setAngles(entity, limbSwingAmount, limbSwing, animationProgress, netHeadYaw, 0);

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = !entity.isInvisible();
        boolean bl2 = entity.isInvisibleTo(minecraftClient.player);
        boolean bl3 = minecraftClient.hasOutline(entity);
        RenderLayer renderLayer = this.getRenderLayer(entity, bl, bl2, bl3);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            int q = getOverlay(entity, 0.0f);
            this.model.render(matrices, vertexConsumer, i, q, 1.0F, 1.0F, 1.0F, bl2 ? 0.15F : 1.0F);
        }

        if (!entity.isSpectator()) {
            Iterator var25 = this.features.iterator();

            while(var25.hasNext()) {
                FeatureRenderer<T, M> featureRenderer = (FeatureRenderer)var25.next();
                featureRenderer.render(matrices, vertexConsumerProvider, i, entity, limbSwingAmount, limbSwing, tickDelta, animationProgress, netHeadYaw, 0);
            }
        }

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, i);

        otherModelParts(entity, matrices, vertexConsumerProvider, tickDelta);
        matrices.pop();
    }

    protected void otherModelParts(T entity, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, float tickDelta) {
    }


    protected boolean doBobbingAnimation() {
        return true;
    }

    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, int i) {
    }

    public M getModel() {
        return this.model;
    }

    protected void scale(T entity, MatrixStack matrices) {
        if (entity.isBookRender()) {
            guiScale(matrices);
        }
        else {
            worldScale(matrices);
        }
    }

    protected void scale(MatrixStack matrices, float i) {
        matrices.scale(i, i, i);
    }

    protected abstract void guiScale(MatrixStack matrices);

    protected abstract void worldScale(MatrixStack matrices);

    @Nullable
    protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }
    public int getOverlay(T entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(false));
    }
    protected float getAnimationProgress(T entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }
    private float getHandSwingProgress(T entity, float tickDelta) {
        return entity.getHandSwingProgress(tickDelta);
    }
}