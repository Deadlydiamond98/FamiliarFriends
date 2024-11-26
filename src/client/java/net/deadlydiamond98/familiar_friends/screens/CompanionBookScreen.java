package net.deadlydiamond98.familiar_friends.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.CompanionBookAdd;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.FamiliarFriendsClient;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CompanionBookScreen extends HandledScreen<CompanionBookScreenHandler> {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private float mouseX;
    private float mouseY;

    private int pageIndex;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;

    public CompanionBookScreen(CompanionBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        CompanionBookAdd.addCompanions();
        this.addPageButtons();
    }

    protected void addPageButtons() {
        int i = (this.width - 320) / 2;
        this.nextPageButton = this.addDrawableChild(new PageTurnWidget(i + 280, 170, true, (button) -> this.goToNextPage(), true));
        this.previousPageButton = this.addDrawableChild(new PageTurnWidget(i + 16, 170, false, (button) -> this.goToPreviousPage(), true));
        this.updatePageButtons();
    }

    protected void goToPreviousPage() {
        if (this.pageIndex > 0) {
            --this.pageIndex;
        }

        this.updatePageButtons();
    }

    protected void goToNextPage() {
        if (this.pageIndex < this.getPageCount() - 1) {
            ++this.pageIndex;
        }

        this.updatePageButtons();
    }

    private void updatePageButtons() {
        this.nextPageButton.visible = this.pageIndex < this.getPageCount() - 1;
        this.previousPageButton.visible = this.pageIndex > 0;
    }


    private int getPageCount() {
        return CompanionBookAdd.companions.size();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);

        int x = (this.width) / 2;
        int y = 2;

        context.drawTexture(BOOK_TEXTURE, (this.width - 320) / 2, y, 8, 2, 320, 200, 512, 512);

        drawEntity(context, x - 80, y + 95, 40, 0.0625F, CompanionBookAdd.companions.get(pageIndex));
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    public static void drawEntity(DrawContext context, int x, int y, int size, float f, PlayerCompanion entity) {
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
        drawEntity(context, x, y, size, vector3f, rotationQuaternion, null, entity);

        entity.bodyYaw = bodyYawBackup;
        entity.setYaw(yawBackup);
        entity.setPitch(pitchBackup);
        entity.prevHeadYaw = prevHeadYawBackup;
        entity.headYaw = headYawBackup;
    }

    public static void drawEntity(DrawContext context, float x, float y, float size, Vector3f vector3f, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, PlayerCompanion entity) {
        context.getMatrices().push();
        context.getMatrices().translate((double)x, (double)y, 50.0);
        context.getMatrices().scale(size, size, -size);
        context.getMatrices().translate(vector3f.x, vector3f.y, vector3f.z);
        context.getMatrices().multiply(quaternionf);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null) {
            entityRenderDispatcher.setRotation(quaternionf2.conjugate(new Quaternionf()).rotateY((float) Math.toRadians(180)));
        }

        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, context.getMatrices(), context.getVertexConsumers(), 15728880);
        });
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
