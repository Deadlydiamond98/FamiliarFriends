package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
        super(CompanionEntities.Ocelot_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 0, true, false));
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.ocelot");
    }
}
