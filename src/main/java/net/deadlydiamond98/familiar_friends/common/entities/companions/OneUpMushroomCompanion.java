package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class OneUpMushroomCompanion extends PlayerCompanion {
    public OneUpMushroomCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public OneUpMushroomCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.One_Up_Mushroom_Companion, world, owner, gui);
    }

    @Override
    public void onPlayerDeath(PlayerEntity player) {
        player.setHealth(player.getMaxHealth());
        this.playSound(CompanionSounds.One_Up, 1.0f, 1.0f);
        player.lockCompanion(this.getType().getTranslationKey());
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.OneUp.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.OneUp.enabled;
    }
}
