package net.deadlydiamond98.familiar_friends.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.CompanionRegistry;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionBookButton;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionHomeButton;
import net.deadlydiamond98.familiar_friends.screens.widgets.CompanionIconButton;
import net.deadlydiamond98.familiar_friends.util.CompanionGuiDrawMethods;
import net.deadlydiamond98.familiar_friends.util.TimeUnitHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompanionBookScreen extends Screen {
    private static final int SCROLL_STEP = 10;

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private final List<PlayerCompanion> renderedCompanions = new ArrayList<>();
    private final List<CompanionIconButton> legendButtons = new ArrayList<>();
    private final List<Drawable> drawables = Lists.newArrayList();

    private int pageIndex;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    
    private CompanionBookButton unlockButton;
    private CompanionBookButton equipButton;
    private CompanionBookButton unequipButton;
    private CompanionHomeButton homeButton;

    private int scrollOffset;
    private int maxScroll;

    private int indexOffset;
    private boolean listLoaded;

    public CompanionBookScreen(Text title) {
        super(title);
        this.scrollOffset = 0;
        this.maxScroll = 0;
        this.indexOffset = 0;
        this.listLoaded = false;
    }

    @Override
    protected void init() {
        init(true);
    }

    private void init(boolean createCompanionList) {
        this.clearChildren();
        this.legendButtons.clear();
        this.drawables.clear();

        if (createCompanionList) {
            this.addCompanions();
        }

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
        renderedCompanions.clear();
        addCompanionsToBook(client.player);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderInGameBackground(context);
        this.drawBackground(context, delta, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        updateEntityButtons();
        updatePageButtons();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MatrixStack matrices = context.getMatrices();

        int guiX = (this.width) / 2;
        int guiY = 2;

        context.drawTexture(BOOK_TEXTURE, (this.width - 320) / 2, guiY, 8, 2, 320, 200, 512, 512);

        // Stop execution of drawEntity if no companions in book to prevent crashing
        if (renderedCompanions.isEmpty()) {
            return;
        }

        // If on the first page, render the page selection area rather than an entity
        if (this.pageIndex == 0) {
            drawFirstPage(context, delta, mouseX, mouseY, guiX, guiY);
            return;
        }

        // Entity Page

        PlayerCompanion companion = renderedCompanions.get(pageIndex - 1);

        int companionX = guiX - 80;
        int companionY = guiY + 110;

        if (companion.isLocked(client.player)) {
            context.drawTexture(BOOK_TEXTURE, ((this.width - 11) / 2) - 140, companionY - 100, 12, 246, 11, 15, 512, 512);

            Text xpCost = companion.getCostLang(companion.getCostClient()).copy().setStyle(Style.EMPTY.withUnderline(true));

            CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                    matrices, companionX, companionY - 95, xpCost, 1.0f, 0x478e47, false); // Experience Cost
        }
        else {
            Text xpCost = Text.translatable("gui.familiar_friends.unlocked").setStyle(Style.EMPTY.withUnderline(true).withColor(0x478e47));

            CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                    matrices, companionX, companionY - 95, xpCost, 1.0f, 0x478e47, false); // Experience Cost
        }

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

        PlayerCompanion playerCompanion = CompanionRegistry.createCompanion(player.currentCompanion(), player, true);

        Text currentText = Text.translatable("gui.familiar_friends.current_companion").setStyle(Style.EMPTY.withUnderline(true));

        CompanionGuiDrawMethods.drawResizeableCenteredText(textRenderer, context,
                matrices, companionX, companionY - 95, currentText, 1.25f, 0x574436, false); // Current Companion Text

        context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) - 80, companionY - 83, 329, 2, 106, 106, 512, 512); // Entity frame thing

        if (playerCompanion != null) {
            showCurrentCompanionGUI(context, delta, mouseX, mouseY, companionX, companionY, playerCompanion, matrices);
        }

        context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) + 65, companionY - 98, 56, 266, 126, 160, 512, 512); // Entity frame thing 2

        drawScrollBar(context, guiX + 138, companionY - 98, 158);
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

    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        if (this.pageIndex == 0) {
            return;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);

        PlayerCompanion companion = renderedCompanions.get(pageIndex - 1);

        // Lock Overlay
        if (companion.isLocked(client.player)) {
            context.drawTexture(BOOK_TEXTURE, ((this.width - 106) / 2) - 82, 27, 110, 110, 57, 204,
                    56, 56, 512, 512);
        }
    }

    // Scroll Bar

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount < 0) {
            scrollOffset = Math.min(scrollOffset + SCROLL_STEP, maxScroll);
        } else if (verticalAmount > 0) {
            scrollOffset = Math.max(scrollOffset - SCROLL_STEP, 0);
        }

        legendButtons.forEach(button -> {
            button.scroll(-scrollOffset);
        });
        updateLegendButtons();

        return true;
    }

    private void drawScrollBar(DrawContext context, int guiX, int guiY, int height) {
        int scrollBarHeight = height;
        int barWidth = 5;

        context.fill(guiX, guiY, guiX + barWidth, guiY + scrollBarHeight, 0xff95836a);

        // Avoid creating Scroll bar thumb if unnecessary
        if (maxScroll > 0) {
            int scrollBarThumbHeight = Math.max(20, (scrollBarHeight * scrollBarHeight) / (scrollBarHeight + maxScroll));
            int scrollThumbY = guiY + (scrollOffset * (scrollBarHeight - scrollBarThumbHeight) / maxScroll);
            context.fill(guiX, scrollThumbY, guiX + barWidth, scrollThumbY + scrollBarThumbHeight, 0xff574436);
        }
    }

    // Only using render, so I can scissor some buttons for scrolling, there might be a better way,
    // but right now I'm running on low sleep (I have a horrible sleep schedule), so I'm just worried about it working
    // no one other than me will be interacting with this part of the code anyways

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!listLoaded) {
            return;
        }

        this.renderBackground(context, mouseX, mouseY, delta);
        Iterator var5 = this.drawables.iterator();

        List<CompanionIconButton> iconButtons = new ArrayList<>();
        while(var5.hasNext()) {
            Drawable drawable = (Drawable)var5.next();

            if (drawable instanceof CompanionIconButton iconButton) {
                int guiX = (this.width) / 2;
                int guiY = 2;
                context.enableScissor(guiX, guiY + 14, guiX + 160, guiY + 164);
                drawable.render(context, mouseX, mouseY, delta);
                context.disableScissor();
                iconButtons.add(iconButton);
            }
            else {
                drawable.render(context, mouseX, mouseY, delta);
            }
        }

        iconButtons.forEach(iconButton -> {
            if (iconButton.visible) {
                iconButton.renderTooltip(context, textRenderer, mouseX, mouseY);
            }
        });

        RenderSystem.disableDepthTest();

        this.drawForeground(context, mouseX, mouseY);

        RenderSystem.enableDepthTest();
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
        this.unequipButton = this.addDrawableChild(new CompanionBookButton(i - 80, 176, 100, 15,
                Text.translatable("gui.familiar_friends.unequip"), button -> this.unequipFamiliar()));
        this.homeButton = this.addDrawableChild(new CompanionHomeButton(i + 80, 175, 12, 12,
                button -> this.goHome()));
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

        for (int i = 0; i < renderedCompanions.size(); i++) {
            int row = i / buttonsPerRow;
            int col = i % buttonsPerRow;

            if (row >= 5 && col == 0) {
                this.maxScroll += buttonHeight + spacing;
            }

            int buttonX = guiX + col * (buttonWidth + spacing);
            int buttonY = guiY + row * (buttonHeight + spacing);

            CompanionIconButton legendButton = new CompanionIconButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    i, renderedCompanions.get(i).isLocked(client.player),
                    renderedCompanions.get(i), button -> this.onLegendButtonClicked((CompanionIconButton) button));

            legendButtons.add(this.addDrawableChild(legendButton));
        }
    }

    // Button Pressings

    private void onLegendButtonClicked(CompanionIconButton button) {
        pageIndex = button.getEntityIndex() + 1;
        updateButtons();
    }

    private void unlockFamiliar() {
        PlayerCompanion companion = renderedCompanions.get(pageIndex - 1);
        CompanionClientPackets.unlockPlayerCompanion(companion.getType().getTranslationKey());
        this.unlockButton.visible = false;
        this.equipButton.visible = true;
    }

    private void equipFamiliar() {
        PlayerCompanion companion = renderedCompanions.get(pageIndex - 1);
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

    private void goHome() {
        this.pageIndex = 0;
        updateButtons();
    }


    // Update Button Viewability

    private void updateEntityButtons() {
        boolean pastFirstPage = this.pageIndex > 0;

        PlayerEntity player = client.player;
        PlayerCompanion companion = pastFirstPage ? renderedCompanions.get(pageIndex - 1) : null;

        boolean companionExists = companion != null;
        boolean companionLocked = companionExists && companion.isLocked(player);
        boolean hasCompanion = player.getCompanion() != null;
        boolean hasDifferentCompanion = companionExists &&
                !companion.getType().getTranslationKey().equals(player.currentCompanion());

        // Update button visibility
        this.unlockButton.visible = pastFirstPage && companionLocked;
        this.equipButton.visible = pastFirstPage && !companionLocked;
        this.unequipButton.visible = !pastFirstPage;
        this.homeButton.visible = pastFirstPage;

        // Update button active states
        this.equipButton.active = hasDifferentCompanion && player.getCompanionCooldown() <= 0;
        this.unequipButton.active = hasCompanion;
        this.unlockButton.active = pastFirstPage && companionExists &&
                player.experienceLevel >= companion.getCostClient();

        if (player.getCompanionCooldown() > 0) {
            this.equipButton.setMessage(
                    Text.literal(TimeUnitHelper.calculateCooldownUnit(player.getCompanionCooldown()) + " ")
                            .append(Text.translatable("cooldown.familiar_friends." +
                                    TimeUnitHelper.getCooldownUnitText((float) player.getCompanionCooldown()))));
        }
        else {
            this.equipButton.setMessage(Text.translatable("gui.familiar_friends.equip"));
        }
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
        legendButtons.forEach(button -> {
            button.visible = this.pageIndex < 1 && button.getYScrolled() > 0 && button.getYScrolled() < 180;;
            button.locked = renderedCompanions.get(button.getEntityIndex()).isLocked(client.player);
        });
    }

    // Other

    private int getPageCount() {
        return renderedCompanions.size() + 1;
    }

    @Override
    protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        this.drawables.add(drawableElement);
        return super.addDrawableChild(drawableElement);
    }

    public void addCompanionsToBook(PlayerEntity player) {

        CompanionRegistry.COMPANIONS.forEach((key, companionClass) -> {
            PlayerCompanion companion = CompanionRegistry.createCompanion(key, player, true);

            if (companion != null) {
                renderedCompanions.add(companion);
            }
        });

        if (renderedCompanions.isEmpty()) {
            FamiliarFriends.LOGGER.info("No Companions added to the book :<");
        }

        // Sort Alphabetically, taking language into account
        renderedCompanions.sort((o1, o2) -> {
            String name1 = o1.getName().getString();
            String name2 = o2.getName().getString();
            return name1.compareToIgnoreCase(name2);
        });

        for (int i = 0; i < renderedCompanions.size(); i++) {
            CompanionClientPackets.sendCompanion(renderedCompanions.get(i).getType().getTranslationKey(), i);
        }
    }

    public void syncCompanionData(int cost, boolean enabled, int index) {
        int i = index - indexOffset;
        renderedCompanions.get(i).syncClientData(cost, enabled);
        if (!enabled) {
            renderedCompanions.remove(i);
            init(false);
            indexOffset++;
        }

        if (i == renderedCompanions.size() - 1) {
            listLoaded = true;
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
