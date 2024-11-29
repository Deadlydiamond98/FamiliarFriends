package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NaviCompanion extends PlayerCompanion {
    public NaviCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public NaviCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Navi_Companion, world, owner, gui);
    }

    @Override
    protected void lookBehavior() {
        if (this.getOwner() != null) {
            float ownerYaw = this.getOwner().getHeadYaw();
            this.setYaw(ownerYaw + 20);
        }
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        int radius = 20;

        BlockPos closestBlockPos = null;

        World world = player.getWorld();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos blockPos = new BlockPos((int) (player.getX() + x), (int) (player.getY() + y), (int) (player.getZ() + z));
                    Block block = world.getBlockState(blockPos).getBlock();

                    if (block.getDefaultState().isOf(Blocks.ANCIENT_DEBRIS) ||
                            block.getDefaultState().isOf(Blocks.DEEPSLATE_DIAMOND_ORE) ||
                            block.getDefaultState().isOf(Blocks.DIAMOND_ORE)) {
                        if (closestBlockPos == null || getClosest(player, closestBlockPos, blockPos)) {
                            closestBlockPos = blockPos;
                        }
                    }
                }
            }
        }

        if (closestBlockPos != null) {

            float distance = (float) player.squaredDistanceTo(closestBlockPos.getX(), closestBlockPos.getY(), closestBlockPos.getZ());

            int frequency = Math.max(20, (int) (distance * 1.5));

            if (this.age % frequency == 0) {

                float pitch = (Math.max(-2.0f, 2.0f / (distance + 1)) + 0.5f) * 2;

                this.playSound(CompanionSounds.Navi, 1.0f, pitch);
            }
        }

    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {

    }

    private boolean getClosest(PlayerEntity player, BlockPos currentClosest, BlockPos newPos) {
        double currentDistance = player.squaredDistanceTo(currentClosest.getX(), currentClosest.getY(), currentClosest.getZ());
        double newDistance = player.squaredDistanceTo(newPos.getX(), newPos.getY(), newPos.getZ());
        return newDistance < currentDistance;
    }

    @Override
    public int getCost() {
        return 20;
    }

}
