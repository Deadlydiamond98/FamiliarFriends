package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TinyTaterCompanion extends PlayerCompanion {

    public TinyTaterCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Tiny_Tater_Companion, world, owner, gui);
    }

    public TinyTaterCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        if (this.hasNoCooldown(player)) {
            // Long cooldown due to spam-ability
            this.playSound(CompanionSounds.Do_It, 0.25f, 1.0f);
            this.setCooldownSeconds(15);
        }
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target, float amount) {
        if (player.getRandom().nextInt(10) == 5) {
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
            player.getHungerManager().add((int) (amount), 1);
            if (target instanceof PlayerEntity targetPlayer) {
                targetPlayer.getHungerManager().addExhaustion((int) (amount));
            }
        }
    }

    @Override
    public int getCost() {
        return CompanionConfig.taterCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.taterEnabled;
    }
}
