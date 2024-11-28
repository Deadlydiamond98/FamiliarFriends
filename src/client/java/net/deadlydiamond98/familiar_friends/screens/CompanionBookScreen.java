package net.deadlydiamond98.familiar_friends.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionBookButton;
import net.deadlydiamond98.familiar_friends.util.CompanionGuiDrawMethods;
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
import net.minecraft.entity.EntityType;
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

    @Override
    protected void init() {
        this.addCompanions();
        this.addPageButtons();
        this.addEntityButtons();
    }

    private void addCompanions() {
        if (client.player == null) {
            FamiliarFriends.LOGGER.info("Tried to add Companions to book, but player was null");
            return;
        }
        RENDERED_COMPANIONS.clear();
        RENDERED_COMPANIONS.addAll(CompanionRegistry.addCompanions(client.player));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MatrixStack matrices = context.getMatrices();

        int guiX = (this.width) / 2;
        int guiY = 2;

        context.drawTexture(BOOK_TEXTURE, (this.width - 320) / 2, guiY, 8, 2, 320, 200, 512, 512);


        updateEntityButtons();
        updatePageButtons();

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

        CompanionGuiDrawMethods.drawEntity(context, companionX, companionY, 40, 0.0625F, companion, companion.isLocked(client.player)); // Entity Model

        Text name = companion.getName().copy().setStyle(Style.EMPTY.withUnderline(true));

        companionY += 25;

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY, name, 1.25f, 0x574436, false); // Entity Name

        companionY += 15;

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY, companion.getAuthor(), 0.55f, 0x95836a, true); // Entity Author


        // Description Page

        int descriptionX = guiX + 80;
        int descriptionY = guiY + 15;

        Text descriptionTitle = Text.translatable("gui.familiar_friends.desc").setStyle(Style.EMPTY.withUnderline(true));

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, descriptionX, descriptionY, descriptionTitle, 1.0f, 0x574436, false); // Description Title

        descriptionY += 15;

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, descriptionX, descriptionY, companion.getDescription(), 0.75f, 0x766450, false); // Companion Description
    }

    // TODO: add main page for easier navigation!
    private void drawFirstPage(DrawContext context, float delta, int mouseX, int mouseY, int x, int y) {
        MatrixStack matrices = context.getMatrices();
        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, x, y + 90, Text.literal("Put Page Select Here Later!"), 1.0f, 0x000000, false);
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

        boolean bl = true;
        if ((((CompanionPlayerData) client.player).currentCompanion() != null && companion != null)) {
            bl = !companion.getType().getTranslationKey().equals(((CompanionPlayerData) client.player).currentCompanion());
        }

        this.equipButton.active = bl;
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
