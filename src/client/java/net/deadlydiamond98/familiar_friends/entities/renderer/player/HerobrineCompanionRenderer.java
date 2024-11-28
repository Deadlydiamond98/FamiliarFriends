package net.deadlydiamond98.familiar_friends.entities.renderer.player;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.companions.HerobrineCompanion;
import net.deadlydiamond98.familiar_friends.entities.renderer.BipedCompanionRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class HerobrineCompanionRenderer extends BipedCompanionRenderer<HerobrineCompanion> {

    private static final Identifier TEXTURE = Identifier.of(FamiliarFriends.MOD_ID, "textures/entity/herobrine.png");

    public HerobrineCompanionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(HerobrineCompanion entity) {
        return TEXTURE;
    }
}
