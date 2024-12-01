package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class OcelotCompanion extends PlayerCompanion {
    public OcelotCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public OcelotCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Ocelot_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 23, 0, true, false));
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.ocelot");
    }

    @Override
    public int getCost() {
        return 8;
    }
}
