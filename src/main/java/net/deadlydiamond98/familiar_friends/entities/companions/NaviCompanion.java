package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
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
    protected void doPassiveAction(PlayerEntity player, Entity nearestHostile) {
        int radius = 10;

        BlockPos closestBlockPos = null;

        World world = player.getWorld();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos blockPos = new BlockPos((int) (player.getX() + x), (int) (player.getY() + y), (int) (player.getZ() + z));
                    Block block = world.getBlockState(blockPos).getBlock();

                    if (block.getDefaultState().isOf(Blocks.DIAMOND_ORE)) {
                        if (closestBlockPos == null || getClosest(player, closestBlockPos, blockPos)) {
                            closestBlockPos = blockPos;
                        }
                    }
                }
            }
        }

        if (closestBlockPos != null) {

            float distance = (float) player.squaredDistanceTo(closestBlockPos.getX(), closestBlockPos.getY(), closestBlockPos.getZ());

            int frequency = Math.max(20, (int) (distance * 2.5));

            if (this.age % frequency == 0) {

                float pitch = (Math.max(-2.0f, 2.0f / (distance + 1)) + 0.5f) * 2;

                world.playSound(this, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0f, pitch);
            }
        }

    }

    private boolean getClosest(PlayerEntity player, BlockPos currentClosest, BlockPos newPos) {
        double currentDistance = player.squaredDistanceTo(currentClosest.getX(), currentClosest.getY(), currentClosest.getZ());
        double newDistance = player.squaredDistanceTo(newPos.getX(), newPos.getY(), newPos.getZ());
        return newDistance < currentDistance;
    }

}
