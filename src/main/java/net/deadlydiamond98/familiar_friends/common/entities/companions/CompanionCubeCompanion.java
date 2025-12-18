package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CompanionCubeCompanion extends PlayerCompanion {
    public CompanionCubeCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CompanionCubeCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Companion_Cube_Companion, world, owner, gui);
    }

    @Override
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {
        return FamiliarFriendsConfig.CompanionCube.grantFireImmunity && source.isIn(DamageTypeTags.IS_FIRE);
    }

    @Override
    public @Nullable TagKey<Fluid> walkableFluids() {
        return FluidTags.LAVA;
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.CompanionCube.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.CompanionCube.enabled;
    }
}
