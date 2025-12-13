package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.common.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MrSaturnCompanion extends PlayerCompanion {
    public MrSaturnCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public MrSaturnCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Mr_Saturn_Companion, world, owner, gui);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target, float amount) {
        if (player.getRandom().nextInt(15) == 4) {

            target.damage(target.getDamageSources().magic(), amount + 2);

            double x = player.getX() - target.getX();
            double y = player.getZ() - target.getZ();

            target.takeKnockback(3, x, y);
            this.playSound(CompanionSounds.Smmaaash, 1.0f, 1.0f);
        }
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.MrSaturn.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.MrSaturn.enabled;
    }
}
