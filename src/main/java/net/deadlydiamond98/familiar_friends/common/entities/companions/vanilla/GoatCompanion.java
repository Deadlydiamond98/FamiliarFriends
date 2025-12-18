package net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class GoatCompanion extends PlayerCompanion {
    public GoatCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public GoatCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Goat_Companion, world, owner, gui);
    }

    @Override
    public boolean canWalkOnPowderSnow() {
        return true;
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.goat");
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Goat.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Goat.enabled;
    }
}
