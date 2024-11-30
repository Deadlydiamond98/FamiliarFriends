package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SpiderCompanion extends PlayerCompanion {
    public SpiderCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SpiderCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Spider_Companion, world, owner, gui);
    }

    @Override
    public boolean canClimbWalls() {
        return true;
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.spider");
    }

    @Override
    public int getCost() {
        return 20;
    }
}
