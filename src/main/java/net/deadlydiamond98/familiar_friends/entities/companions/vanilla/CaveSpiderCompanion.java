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

public class CaveSpiderCompanion extends PlayerCompanion {
    public CaveSpiderCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CaveSpiderCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Cave_Spider_Companion, world, owner, gui);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        if (player.getRandom().nextInt(10) == 5) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 3, 0));
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.cave_spider");
    }

    @Override
    public int getCost() {
        return 12;
    }
}
