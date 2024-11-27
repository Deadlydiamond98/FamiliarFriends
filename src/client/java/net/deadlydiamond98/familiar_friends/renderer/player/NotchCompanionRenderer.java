package net.deadlydiamond98.familiar_friends.renderer.player;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.NotchCompanion;
import net.deadlydiamond98.familiar_friends.renderer.BipedCompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class NotchCompanionRenderer extends BipedCompanionRenderer<NotchCompanion> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/notch.png");

    public NotchCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(NotchCompanion entity) {
        return TEXTURE;
    }
}
