package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.init.CompanionBlocks;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.common.entities.projectiles.CirnoProjectile;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.block.FluidBlock.LEVEL;

public class CirnoCompanion extends PlayerCompanion {
    public CirnoCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public CirnoCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Cirno_Companion, world, owner, gui);
    }

    @Override
    public void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (FamiliarFriendsConfig.Cirno.enableFrostWalker) {
            World world = player.getWorld();
            BlockPos playerPos = player.getBlockPos();

            if (!world.getBlockState(playerPos.down()).isOf(Blocks.WATER) && !world.getBlockState(playerPos.down()).isAir()
                    && !player.isTouchingWater() && !world.getBlockState(playerPos).isOf(Blocks.WATER)) {
                int radius = 3;
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos blockPos = playerPos.add(x, -1, z);
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.isOf(Blocks.WATER) && blockState.get(LEVEL) == 0) {
                            world.setBlockState(blockPos, CompanionBlocks.Cirno_Ice.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        if (this.hasNoCooldown(player)) {
            Vec3d lookDirection = player.getRotationVec(1);

            Vec3d offset1 = new Vec3d(lookDirection.z, 0, -lookDirection.x).normalize().multiply(0.5);
            Vec3d offset2 = new Vec3d(-lookDirection.z, 0, lookDirection.x).normalize().multiply(0.5);

            Vec3d projectile1Direction = lookDirection.add(offset1).normalize();
            Vec3d projectile2Direction = lookDirection.add(offset2).normalize();

            spawnProjectile(player, projectile1Direction);
            spawnProjectile(player, projectile2Direction);

            this.playSound(CompanionSounds.Cirno_Shoot, 0.5f, 1.0f);
            this.setCooldownSeconds(FamiliarFriendsConfig.Cirno.projectileCooldown);
        }
    }

    private void spawnProjectile(PlayerEntity player, Vec3d direction) {
        CirnoProjectile projectile = new CirnoProjectile(CompanionEntityTypes.Cirno_Projectile, player.getWorld());
        projectile.setOwner(player);
        projectile.setPos(player.getX(), player.getEyeY() - 0.5, player.getZ());
        projectile.setVelocity(direction.x, direction.y, direction.z, 1.5F, 1.0F);
        player.getWorld().spawnEntity(projectile);
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Cirno.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Cirno.enabled;
    }
}
