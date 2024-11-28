package net.deadlydiamond98.familiar_friends.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionBookButton;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionIconButton;
import net.deadlydiamond98.familiar_friends.util.CompanionGuiDrawMethods;
import net.deadlydiamond98.familiar_friends.util.TextFormatHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CompanionBookScreen extends HandledScreen<CompanionBookScreenHandler> {


    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private final List<PlayerCompanion> RENDERED_COMPANIONS = new ArrayList<>();
    private final List<CompanionIconButton> LEGEND_BUTTONS = new ArrayList<>();

    private int pageIndex;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    
    private CompanionBookButton unlockButton;
    private CompanionBookButton equipButton;
    private CompanionBookButton unequipButton;

    public CompanionBookScreen(CompanionBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        this.addCompanions();
        this.addLegendButtons();
        this.addPageButtons();
        this.addEntityButtons();
        this.updateButtons();
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

    private void drawFirstPage(DrawContext context, float delta, int mouseX, int mouseY, int guiX, int guiY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        MatrixStack matrices = context.getMatrices();

        int companionX = guiX - 80;
        int companionY = guiY + 108;

        PlayerEntity player = client.player;

        PlayerCompanion playerCompanion = player.getCompanion();

        Text currentText = Text.translatable("gui.familiar_friends.current_companion").setStyle(Style.EMPTY.withUnderline(true));

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY - 95, currentText, 1.25f, 0x574436, false); // Current Companion Text

        context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) - 80, companionY - 83, 329, 2, 106, 106, 512, 512); // Entity frame thing

        if (playerCompanion != null) {
            showCurrentCompanionGUI(context, delta, mouseX, mouseY, companionX, companionY, playerCompanion, matrices);
        }

    }

    private void showCurrentCompanionGUI(DrawContext context, float delta, int mouseX, int mouseY, int companionX, int companionY, PlayerCompanion playerCompanion, MatrixStack matrices) {
        CompanionGuiDrawMethods.drawEntity(context, companionX, companionY, 40, 0.0625F, playerCompanion, playerCompanion.isLocked(client.player)); // Entity Model

        Text name = playerCompanion.getName().copy().setStyle(Style.EMPTY.withUnderline(true));

        companionY += 25;

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY, name, 1.25f, 0x574436, false); // Entity Name

        companionY += 15;

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY, playerCompanion.getAuthor(), 0.55f, 0x95836a, true); // Entity Author
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

    // Add Buttons

    protected void addPageButtons() {
        int i = (this.width - 320) / 2;
        this.nextPageButton = this.addDrawableChild(new PageTurnWidget(i + 280, 170, true, (button) -> this.goToNextPage(), true));
        this.previousPageButton = this.addDrawableChild(new PageTurnWidget(i + 16, 170, false, (button) -> this.goToPreviousPage(), true));
    }

    private void addEntityButtons() {
        int i = (this.width) / 2;
        this.unlockButton = this.addDrawableChild(new CompanionBookButton(i + 80, 150, 100, 15,
                Text.translatable("gui.familiar_friends.unlock"), button -> this.unlockFamiliar()));
        this.equipButton = this.addDrawableChild(new CompanionBookButton(i + 80, 150, 100, 15,
                Text.translatable("gui.familiar_friends.equip"), button -> this.equipFamiliar()));
        this.unequipButton = this.addDrawableChild(new CompanionBookButton(i - 80, 178, 100, 15,
                Text.translatable("gui.familiar_friends.unequip"), button -> this.unequipFamiliar()));
    }

    private void addLegendButtons() {
        if (client.player == null) {
            FamiliarFriends.LOGGER.info("Tried to render legend book, but player was null");
            return;
        }

        int guiX = ((this.width) / 2) + 30;
        int guiY = 30;
        int buttonWidth = 25;
        int buttonHeight = 25;

        int spacing = 5;
        int buttonsPerRow = 4;

        for (int i = 0; i < RENDERED_COMPANIONS.size(); i++) {
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;

            int buttonX = guiX + col * (buttonWidth + spacing);
            int buttonY = guiY + row * (buttonHeight + spacing);

            LEGEND_BUTTONS.add(this.addDrawableChild(
                    new CompanionIconButton(buttonX, buttonY, buttonWidth, buttonHeight,
                            i, RENDERED_COMPANIONS.get(i).isLocked(client.player),
                            RENDERED_COMPANIONS.get(i), button -> this.onLegendButtonClicked((CompanionIconButton) button))
            ));
        }
    }

    // Button Pressings

    private void onLegendButtonClicked(CompanionIconButton button) {
        pageIndex = button.getEntityIndex() + 1;
        updateButtons();
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

    private void unequipFamiliar() {
        CompanionClientPackets.unequipPlayerCompanion("");
        this.unequipButton.active = false;
        this.updateEntityButtons();
    }

    protected void goToPreviousPage() {
        if (this.pageIndex > 0) {
            --this.pageIndex;
        }
        this.updateButtons();
    }

    protected void goToNextPage() {
        if (this.pageIndex < this.getPageCount() - 1) {
            ++this.pageIndex;
        }
        this.updateButtons();
    }


    // Update Button Viewability

    private void updateEntityButtons() {
        boolean pastFirstPage = this.pageIndex > 0;

        PlayerEntity player = client.player;

        PlayerCompanion companion = null;
        if (pastFirstPage) {
            companion = RENDERED_COMPANIONS.get(pageIndex - 1);
        }

        this.unlockButton.visible = pastFirstPage && companion != null && companion.isLocked(client.player);
        this.equipButton.visible = pastFirstPage && companion != null && !companion.isLocked(client.player);
        this.unequipButton.visible = !pastFirstPage;

        boolean bl = true;
        if ((player.currentCompanion() != null && companion != null)) {
            bl = !companion.getType().getTranslationKey().equals((player.currentCompanion()));
        }

        this.equipButton.active = bl;
        this.unequipButton.active = player.getCompanion() != null;
    }

    private void updatePageButtons() {
        this.nextPageButton.visible = this.pageIndex < this.getPageCount() - 1;
        this.previousPageButton.visible = this.pageIndex > 0;
    }

    private void updateButtons() {
        this.updatePageButtons();
        this.updateEntityButtons();
        this.updateLegendButtons();
    }

    private void updateLegendButtons() {
        LEGEND_BUTTONS.forEach(button -> {
            button.visible = this.pageIndex < 1;
            button.locked = RENDERED_COMPANIONS.get(button.getEntityIndex()).isLocked(client.player);
        });
    }

    // Other

    private int getPageCount() {
        return RENDERED_COMPANIONS.size() + 1;
    }
}
