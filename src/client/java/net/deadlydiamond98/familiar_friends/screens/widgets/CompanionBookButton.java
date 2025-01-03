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
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompanionBookButton extends ButtonWidget {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    private int renderX, renderY;

    public CompanionBookButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x - (width / 2), y - (height / 2), width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.renderX = x;
        this.renderY = y;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        int pressed = this.active ? (this.isSelected() ? 21 : 0) : 42;

        int centerX = this.renderX - (this.getWidth() / 2);
        int centerY = this.renderY - (this.getHeight() / 2);

        context.drawTexture(BOOK_TEXTURE, centerX, centerY, this.getWidth(), this.getHeight(), 114, 203 + pressed,
                200, 20, 512, 512);

        int textColor = this.active ? (this.isSelected() ? 0x700000 : 0x574436) : 0x2a2a2a;

        int textWidth = textRenderer.getWidth(getMessage());
        int textHeight = textRenderer.fontHeight;

        int textX = this.renderX - (textWidth / 2);
        int textY = this.renderY - (textHeight / 2);

        context.drawText(textRenderer, getMessage(), textX, textY, textColor, false);
    }
}
