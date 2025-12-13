package net.deadlydiamond98.familiar_friends.client.rendering.companions.player;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.client.rendering.BipedCompanionRenderer;
import net.deadlydiamond98.familiar_friends.common.entities.companions.JebCompanion;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class JebCompanionRenderer extends BipedCompanionRenderer<JebCompanion> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/jeb.png");

    public JebCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(JebCompanion entity) {
        return TEXTURE;
    }
}
