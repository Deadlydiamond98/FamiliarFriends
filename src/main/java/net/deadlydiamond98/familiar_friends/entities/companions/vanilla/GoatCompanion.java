package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
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
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.goat");
    }

    @Override
    public int getCost() {
        return 3;
    }
}
