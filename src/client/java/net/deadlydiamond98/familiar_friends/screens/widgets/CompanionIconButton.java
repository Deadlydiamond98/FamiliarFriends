package net.deadlydiamond98.familiar_friends.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.CompanionGuiDrawMethods;
import net.deadlydiamond98.familiar_friends.util.TextFormatHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CompanionIconButton extends ButtonWidget {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private int renderX, renderY;

    private int entityIndex;

    public boolean locked;

    private PlayerCompanion companion;

    public CompanionIconButton(int x, int y, int width, int height, int i, boolean locked, PlayerCompanion companion, PressAction onPress) {
        super(x - (width / 2), y - (height / 2), width, height, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.renderX = x;
        this.renderY = y;
        this.entityIndex = i;
        this.locked = locked;
        this.companion = companion;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        int pressed = this.active ? (this.isSelected() ? 21 : 0) : 42;

        int centerX = this.renderX - (this.getWidth() / 2);
        int centerY = this.renderY - (this.getHeight() / 2);

        context.drawTexture(BOOK_TEXTURE, centerX, centerY, this.getWidth(), this.getHeight(), 315, 203 + pressed,
                20, 20, 512, 512);

        CompanionGuiDrawMethods.drawEntity(context, renderX, renderY + 6, 9, 0.0625F, companion, false);

        if (this.locked) {
            context.drawTexture(BOOK_TEXTURE, centerX + 14, centerY + 14, (int) (this.getWidth() * 0.75), (int) (this.getHeight() * 0.75), 336, 203,
                    20, 20, 512, 512);
        }

        if (isHovered()) {
            List<Text> tooltip = new ArrayList<>();
            tooltip.add(companion.getName().copy());
            tooltip.add(companion.getAuthor().copy().setStyle(Style.EMPTY.withColor(0x797979)));
            if (locked) {
                tooltip.add(companion.getCostLang());
            }
            else {
                tooltip.add(Text.translatable("gui.familiar_friends.unlocked").withColor(0x478e47));
            }

            String description = companion.getDescription().getString();

            int maxLen = 30;
            int descriptionColor = 0xffec74;
            while (description.length() > maxLen) {
                int split = TextFormatHelper.findSplitIndex(description, maxLen);
                tooltip.add(Text.literal(description.substring(0, split).trim()).setStyle(Style.EMPTY.withColor(descriptionColor)));
                description = description.substring(split).trim();
            }
            tooltip.add(Text.literal(description).setStyle(Style.EMPTY.withColor(descriptionColor)));

            context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
        }
    }

    public int getEntityIndex() {
        return this.entityIndex;
    }
}
