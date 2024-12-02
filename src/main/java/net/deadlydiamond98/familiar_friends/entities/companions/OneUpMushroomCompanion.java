package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
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
        return CompanionConfig.oneUpCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.oneUpEnabled;
    }
}
