package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class RanaCompanion extends PlayerCompanion {
    public RanaCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public RanaCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Rana_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 23, 1, true, false));
    }

    @Override
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {

        if (source.isIn(DamageTypeTags.IS_FALL)) {
            return true;
        }

        return false;
    }

    @Override
    public int getCost() {
        return 18;
    }
}
