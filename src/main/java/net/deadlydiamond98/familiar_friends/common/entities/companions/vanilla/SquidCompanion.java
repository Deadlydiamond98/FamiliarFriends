package net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SquidCompanion extends PlayerCompanion {
    public SquidCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SquidCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Squid_Companion, world, owner, gui);
    }

    @Override
    public void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (!player.isSubmergedInWater()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, FamiliarFriendsConfig.Squid.waterBreathingDuration, 0, true, false));
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.squid");
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Squid.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Squid.enabled;
    }
}
