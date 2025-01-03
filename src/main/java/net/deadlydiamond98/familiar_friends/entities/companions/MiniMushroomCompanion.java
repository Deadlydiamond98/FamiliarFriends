package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.effects.CompanionEffects;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;

public class MiniMushroomCompanion extends PlayerCompanion {
    public MiniMushroomCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public MiniMushroomCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Mini_Mushroom_Companion, world, owner, gui);
    }

    @Override
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {

        if (source.isIn(DamageTypeTags.IS_FALL) && player.hasStatusEffect(CompanionEffects.MINI)) {
            return true;
        }

        return false;
    }


    @Override
    public void doKeyEvent(PlayerEntity player) {
        if (this.hasNoCooldown(player) && this.hasNoHungerLimitation(player, 5)) {
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - 4);
            player.addStatusEffect(new StatusEffectInstance(CompanionEffects.MINI, 900, 0, true, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 900, 0, true, false));
            this.playSound(CompanionSounds.Power_Up, 0.6f, 1.3f);
            this.setCooldownMinutes(1);
        }
    }

    @Override
    public int getCost() {
        return CompanionConfig.miniCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.miniEnabled;
    }
}
