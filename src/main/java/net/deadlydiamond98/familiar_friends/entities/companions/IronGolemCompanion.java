package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class IronGolemCompanion extends PlayerCompanion {
    public IronGolemCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public IronGolemCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Iron_Golem_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {
        if (nearestHostile != null) {
            if (this.age % 120 == 0) {
                nearestHostile.damage(nearestHostile.getDamageSources().playerAttack(player), 3.0f);
                nearestHostile.setVelocity(nearestHostile.getVelocity().multiply(1, 0, 1).add(0, 1, 0));
            }
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.iron_golem");
    }
}
