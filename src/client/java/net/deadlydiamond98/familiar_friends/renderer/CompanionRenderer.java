package net.deadlydiamond98.familiar_friends.renderer;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
    public void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, g);
        this.model.riding = livingEntity.hasVehicle();
        float h = MathHelper.lerpAngleDegrees(g, livingEntity.prevBodyYaw, livingEntity.bodyYaw);
        float j = MathHelper.lerpAngleDegrees(g, livingEntity.prevHeadYaw, livingEntity.headYaw);
        float k = j - h;
        float l;
        if (livingEntity.hasVehicle()) {
            Entity var11 = livingEntity.getVehicle();
            if (var11 instanceof LivingEntity) {
                LivingEntity livingEntity2 = (LivingEntity)var11;
                h = MathHelper.lerpAngleDegrees(g, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
                k = j - h;
                l = MathHelper.wrapDegrees(k);
                if (l < -85.0F) {
                    l = -85.0F;
                }

                if (l >= 85.0F) {
                    l = 85.0F;
                }

                h = j - l;
                if (l * l > 2500.0F) {
                    h += l * 0.2F;
                }

                k = j - h;
            }
        }

        float m = MathHelper.lerp(g, livingEntity.prevPitch, livingEntity.getPitch());

        k = MathHelper.wrapDegrees(k);
        float n;

        n = this.getAnimationProgress(livingEntity, g);
        this.setupTransforms(livingEntity, matrixStack, n, h, g, 1);
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(livingEntity, matrixStack, g);
        matrixStack.translate(0.0F, -1.501F, 0.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(h));
        float o = 0.0F;
        float p = 0.0F;
        if (!livingEntity.hasVehicle() && livingEntity.isAlive()) {
            o = livingEntity.limbAnimator.getSpeed(g);
            p = livingEntity.limbAnimator.getPos(g);

            if (o > 1.0F) {
                o = 1.0F;
            }
        }

        this.model.animateModel(livingEntity, p, o, g);
        this.model.setAngles(livingEntity, p, o, n, k, m);
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = this.isVisible(livingEntity);
        boolean bl2 = livingEntity.isInvisibleTo(minecraftClient.player);
        boolean bl3 = minecraftClient.hasOutline(livingEntity);
        RenderLayer renderLayer = this.getRenderLayer(livingEntity, bl, bl2, bl3);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            int q = getOverlay(livingEntity, this.getAnimationCounter(livingEntity, g));
            this.model.render(matrixStack, vertexConsumer, i, q, bl2 ? 654311423 : -1);
        }

        if (!livingEntity.isSpectator()) {
            Iterator var25 = this.features.iterator();

            while(var25.hasNext()) {
                FeatureRenderer<T, M> featureRenderer = (FeatureRenderer)var25.next();
                featureRenderer.render(matrixStack, vertexConsumerProvider, i, livingEntity, p, o, g, n, k, m);
            }
        }

        matrixStack.pop();
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

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

    public M getModel() {
        return this.model;
    }

    protected boolean isVisible(T entity) {
        return !entity.isInvisible();
    }

    public int getOverlay(T entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(false));
    }

    protected float getAnimationCounter(T entity, float tickDelta) {
        return 0.0F;
    }

    protected abstract void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale);

    protected void scale(T entity, MatrixStack matrices, float amount) {
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

    protected float getAnimationProgress(T entity, float tickDelta) {
        return (float)entity.age + tickDelta;
    }

    protected float getHandSwingProgress(T entity, float tickDelta) {
        return entity.getHandSwingProgress(tickDelta);
    }
}