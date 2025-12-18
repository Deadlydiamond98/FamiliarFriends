package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BiterCompanion extends PlayerCompanion {
    public BiterCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public BiterCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Biter_Companion, world, owner, gui);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target, float amount) {
        if (target.getHealth() - amount <= 0) {
            player.heal(FamiliarFriendsConfig.Vampire.healAmount);
        }
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Vampire.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Vampire.enabled;
    }
}
