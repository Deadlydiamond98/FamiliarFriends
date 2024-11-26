package net.deadlydiamond98.familiar_friends.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionBookButton;
import net.deadlydiamond98.familiar_friends.util.BookCompanionRegistry;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.deadlydiamond98.familiar_friends.util.TextFormatHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CompanionBookScreen extends HandledScreen<CompanionBookScreenHandler> {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private final List<PlayerCompanion> RENDERED_COMPANIONS = new ArrayList<>();

    private int pageIndex;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    
    private CompanionBookButton unlockButton;
    private CompanionBookButton equipButton;

    public CompanionBookScreen(CompanionBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        this.addCompanions();
        this.addPageButtons();
        this.addEntityButtons();
    }

    private void addCompanions() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            FamiliarFriends.LOGGER.info("Tried to add Companions to book, but player was null");
            return;
        }

        RENDERED_COMPANIONS.clear();

        for (Class<? extends PlayerCompanion> companionClass : BookCompanionRegistry.COMPANIONS) {
            try {

                PlayerCompanion companion = companionClass.getConstructor(
                        World.class,
                        PlayerEntity.class,
                        boolean.class
                ).newInstance(
                        client.player.getWorld(),
                        client.player,
                        true
                );

                RENDERED_COMPANIONS.add(companion);

            } catch (Exception e) {
                FamiliarFriends.LOGGER.info("Failed to add companion: " + companionClass.getSimpleName(), e);
            }
        }

        if (RENDERED_COMPANIONS.isEmpty()) {
            FamiliarFriends.LOGGER.info("No Companions added to the book :<");
        }

        // Sort Alphabetically, taking language into account
        RENDERED_COMPANIONS.sort((o1, o2) -> {
            String name1 = o1.getName().getString();
            String name2 = o2.getName().getString();
            return name1.compareToIgnoreCase(name2);
        });
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MatrixStack matrices = context.getMatrices();

        int guiX = (this.width) / 2;
        int guiY = 2;

        context.drawTexture(BOOK_TEXTURE, (this.width - 320) / 2, guiY, 8, 2, 320, 200, 512, 512);

        // Stop execution of drawEntity if no companions in book to prevent crashing
        if (RENDERED_COMPANIONS.isEmpty()) {
            return;
        }

        // If on the first page, render the page selection area rather than an entity
        if (this.pageIndex == 0) {
            drawFirstPage(context, delta, mouseX, mouseY, guiX, guiY);
            return;
        }

        // Entity Page

        PlayerCompanion companion = RENDERED_COMPANIONS.get(pageIndex - 1);

        int companionX = guiX - 80;
        int companionY = guiY + 105;

        context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) - 80, companionY - 83, 329, 2, 106, 106, 512, 512); // Entity frame thing

        drawEntity(context, companionX, companionY, 40, 0.0625F, companion, companion.isLocked(client.player)); // Entity Model

        Text name = companion.getName().copy().setStyle(Style.EMPTY.withUnderline(true));

        companionY += 25;

        drawResizeableCenteredText(context, matrices, companionX, companionY, name, 1.25f, 0x574436, false); // Entity Name

        companionY += 15;

        drawResizeableCenteredText(context, matrices, companionX, companionY, companion.getAuthor(), 0.55f, 0x95836a, true); // Entity Author


        // Description Page

        int descriptionX = guiX + 80;
        int descriptionY = guiY + 15;

        Text descriptionTitle = Text.translatable("gui.familiar_friends.desc").setStyle(Style.EMPTY.withUnderline(true));

        drawResizeableCenteredText(context, matrices, descriptionX, descriptionY, descriptionTitle, 1.0f, 0x574436, false); // Description Title

        descriptionY += 15;

        drawResizeableCenteredText(context, matrices, descriptionX, descriptionY, companion.getDescription(), 0.75f, 0x766450, false); // Companion Description
    }

    private void drawResizeableCenteredText(DrawContext context, MatrixStack matrices, int companionX, int companionY, Text text, float size, int color, boolean commaSplit) {
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

            drawResizeableCenteredText(context, matrices, companionX, companionY + 10,
                    Text.literal(remainingText).setStyle(style), size, color, commaSplit);
        }
        matrices.push();

        matrices.translate(companionX - nameLength, companionY, 0);
        matrices.scale(size, size, size);

        context.drawText(textRenderer, text, 0, 0, color, false);

        matrices.pop();
    }

    // TODO: add main page for easier navigation!
    private void drawFirstPage(DrawContext context, float delta, int mouseX, int mouseY, int x, int y) {
        MatrixStack martices = context.getMatrices();
        drawResizeableCenteredText(context, martices, x, y + 90, Text.literal("Put Page Select Here Later!"), 1.0f, 0x000000, false);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        if (this.pageIndex == 0) {
            return;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);

        PlayerCompanion companion = RENDERED_COMPANIONS.get(pageIndex - 1);

        // Lock Overlay
        if (companion.isLocked(client.player)) {
            context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) - 82, 22, 110, 110, 57, 204,
                    56, 56, 512, 512);
        }
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

    
    
    // Button Things!!!

    private void addEntityButtons() {
        int i = (this.width) / 2;
        this.unlockButton = this.addDrawableChild(new CompanionBookButton(i + 80, 150, 100, 15,
                Text.translatable("gui.familiar_friends.unlock"), button -> this.unlockFamiliar()));
        this.equipButton = this.addDrawableChild(new CompanionBookButton(i + 80, 150, 100, 15,
                Text.translatable("gui.familiar_friends.equip"), button -> this.equipFamiliar()));

        updateEntityButtons();
    }

    private void unlockFamiliar() {
        PlayerCompanion companion = RENDERED_COMPANIONS.get(pageIndex - 1);
        CompanionClientPackets.unlockPlayerCompanion(companion.getType().getTranslationKey());
        this.unlockButton.visible = false;
        this.equipButton.visible = true;
    }

    private void equipFamiliar() {
        PlayerCompanion companion = RENDERED_COMPANIONS.get(pageIndex - 1);
        CompanionClientPackets.equipPlayerCompanion(companion.getType().getTranslationKey());
        this.equipButton.active = false;
        this.updateEntityButtons();
    }

    private void updateEntityButtons() {
        boolean pastFirstPage = this.pageIndex > 0;

        PlayerCompanion companion = null;
        if (pastFirstPage) {
            companion = RENDERED_COMPANIONS.get(pageIndex - 1);
        }

        this.unlockButton.visible = pastFirstPage && companion != null && companion.isLocked(client.player);
        this.equipButton.visible = pastFirstPage && companion != null && !companion.isLocked(client.player);

        this.equipButton.active = companion != ( (CompanionPlayerData) client.player).currentCompanion();
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
        this.updateEntityButtons();
    }

    protected void goToNextPage() {
        if (this.pageIndex < this.getPageCount() - 1) {
            ++this.pageIndex;
        }

        this.updatePageButtons();
        this.updateEntityButtons();
    }

    private void updatePageButtons() {
        this.nextPageButton.visible = this.pageIndex < this.getPageCount() - 1;
        this.previousPageButton.visible = this.pageIndex > 0;
    }
    
    private int getPageCount() {
        return RENDERED_COMPANIONS.size() + 1;
    }
}
