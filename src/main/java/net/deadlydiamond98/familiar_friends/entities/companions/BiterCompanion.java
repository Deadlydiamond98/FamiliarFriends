package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.effects.CompanionEffects;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
            player.heal(1);
        }
    }

    @Override
    public int getCost() {
        return CompanionConfig.biterCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.biterEnabled;
    }
}
