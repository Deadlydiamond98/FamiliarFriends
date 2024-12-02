package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class CreeperCompanion extends PlayerCompanion {
    public CreeperCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CreeperCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Creeper_Companion, world, owner, gui);
    }

    @Override
    public void onPlayerDeath(PlayerEntity player) {
        player.lockCompanion(this.getType().getTranslationKey());
        player.getWorld().createExplosion(player, player.getX(), player.getY(), player.getZ(),
                4, World.ExplosionSourceType.NONE);
    }

    @Override
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {
        if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            return true;
        }
        return false;
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.creeper");
    }

    @Override
    public int getCost() {
        return CompanionConfig.creeperCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.creeperEnabled;
    }
}
