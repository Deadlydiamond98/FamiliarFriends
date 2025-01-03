package net.deadlydiamond98.familiar_friends.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CompanionGuiDrawMethods {

    public static void drawResizeableCenteredText(TextRenderer textRenderer, DrawContext context, MatrixStack matrices, int companionX, int companionY, Text text, float size, int color, boolean commaSplit) {
        float nameLength = ((float) textRenderer.getWidth(text) / 2) * size;

        float maxLength = 25 / size;
        if (nameLength >= maxLength) {
            String fullText = text.getString();
            Style style = text.getStyle();

            int splitIndex = commaSplit ? TextFormatHelper.findCommaSplitIndex(fullText, (int) maxLength) : -1;

            if (splitIndex == -1) {
                splitIndex = TextFormatHelper.findSplitIndex(fullText, (int) maxLength);
            }

            text = Text.literal(fullText.substring(0, splitIndex).trim()).setStyle(style);
            nameLength = ((float) textRenderer.getWidth(text) / 2) * size;

            String remainingText = fullText.substring(splitIndex).trim();
            if (remainingText.startsWith(",")) {
                remainingText = remainingText.substring(1).trim();
            }

            drawResizeableCenteredText(textRenderer, context, matrices, companionX, companionY + 10,
                    Text.literal(remainingText).setStyle(style), size, color, commaSplit);
        }
        matrices.push();

        matrices.translate(companionX - nameLength, companionY, 0);
        matrices.scale(size, size, size);

        context.drawText(textRenderer, text, 0, 0, color, false);

        matrices.pop();
    }


    // These are modified versions of the drawEntity methods from the inventory

    public static void drawEntity(DrawContext context, int x, int y, int size, float f, PlayerCompanion entity, boolean locked) {
        float rotationAngle = (float)(System.currentTimeMillis() % 10000l) / 10000.0f * 360.0f;
        float bobbingOffset = (float)Math.sin(System.currentTimeMillis() / 500.0) * 0.1f;

        Quaternionf rotationQuaternion = new Quaternionf().rotateY((float)Math.toRadians(rotationAngle)).rotateZ((float) Math.toRadians(180));

        float bodyYawBackup = entity.bodyYaw;
        float yawBackup = entity.getYaw();
        float pitchBackup = entity.getPitch();
        float prevHeadYawBackup = entity.prevHeadYaw;
        float headYawBackup = entity.headYaw;

        entity.bodyYaw = 180.0f;
        entity.setYaw(180.0f);
        entity.setPitch(0.0f);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();

        Vector3f vector3f = new Vector3f(0.0f, entity.getHeight() / 2.0f + f + bobbingOffset, 0.0f);

        if (locked) {
            RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
        } else {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        drawEntity(context, x, y, size, vector3f, rotationQuaternion, null, entity);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        entity.bodyYaw = bodyYawBackup;
        entity.setYaw(yawBackup);
        entity.setPitch(pitchBackup);
        entity.prevHeadYaw = prevHeadYawBackup;
        entity.headYaw = headYawBackup;
    }

    public static void drawEntity(DrawContext context, float x, float y, float size, Vector3f vector3f, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, PlayerCompanion entity) {
        context.getMatrices().push();
        context.getMatrices().translate((double)x, (double)y, 50.0);
        context.getMatrices().multiplyPositionMatrix((new Matrix4f()).scaling((float)size, (float)size, (float)(-size)));
        context.getMatrices().translate(vector3f.x, vector3f.y, vector3f.z);
        context.getMatrices().multiply(quaternionf);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null) {
            quaternionf2.conjugate();
            entityRenderDispatcher.setRotation(quaternionf2);
        }

        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, context.getMatrices(), context.getVertexConsumers(), 15728880);
        });
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
