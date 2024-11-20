package net.deadlydiamond98.familiar_friends.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompanionBookScreen extends HandledScreen<CompanionBookScreenHandler> {

    private final Identifier BOOK_TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/gui/companion_book.png");

    public CompanionBookScreen(CompanionBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BOOK_TEXTURE);

        context.drawTexture(BOOK_TEXTURE, (this.width - 320) / 2, 2, 8, 2, 320, 200, 512, 512);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }
}
