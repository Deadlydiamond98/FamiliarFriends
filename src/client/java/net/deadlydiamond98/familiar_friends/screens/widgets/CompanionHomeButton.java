package net.deadlydiamond98.familiar_friends.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompanionHomeButton extends ButtonWidget {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private int renderX, renderY;

    public CompanionHomeButton(int x, int y, int width, int height, PressAction onPress) {
        super(x - (width / 2), y - (height / 2), width, height, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.renderX = x;
        this.renderY = y;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        int pressed = this.isSelected() ? 13 : 0;

        int centerX = this.renderX - (this.getWidth() / 2);
        int centerY = this.renderY - (this.getHeight() / 2);

        context.drawTexture(BOOK_TEXTURE, centerX, centerY, 12 + pressed, 233,
                12, 12, 512, 512);

        int textColor = this.active ? (this.isSelected() ? 0x700000 : 0x574436) : 0x2a2a2a;

        int textWidth = textRenderer.getWidth(getMessage());
        int textHeight = textRenderer.fontHeight;

        int textX = this.renderX - (textWidth / 2);
        int textY = this.renderY - (textHeight / 2);

        context.drawText(textRenderer, getMessage(), textX, textY, textColor, false);
    }

    public void playDownSound(SoundManager soundManager) {
        soundManager.play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
    }
}
