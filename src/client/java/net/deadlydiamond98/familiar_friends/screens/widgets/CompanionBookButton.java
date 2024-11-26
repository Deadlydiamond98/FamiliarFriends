package net.deadlydiamond98.familiar_friends.screens.widgets;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompanionBookButton extends ButtonWidget {

    private static final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    public CompanionBookButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        int pressed = this.isSelected() ? 21 : 0;

        context.drawTexture(BOOK_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 114, 203 + pressed, 200, 20, 512, 512);

        int textColor = this.isSelected() ? 0x700000 : 0x574436;

        int textWidth = textRenderer.getWidth(getMessage());
        int textHeight = textRenderer.fontHeight;

        int textX = this.getX() + (this.getWidth() - textWidth) / 2;
        int textY = this.getY() + (this.getHeight() - textHeight) / 2;

        context.drawText(textRenderer, getMessage(), textX, textY, textColor, false);
    }
}
