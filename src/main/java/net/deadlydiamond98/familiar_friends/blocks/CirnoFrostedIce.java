package net.deadlydiamond98.familiar_friends.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CirnoFrostedIce extends IceBlock {

    public static final MapCodec<CirnoFrostedIce> CODEC = createCodec(CirnoFrostedIce::new);

    public static final IntProperty AGE = Properties.AGE_3;

    public MapCodec<CirnoFrostedIce> getCodec() {
        return CODEC;
    }

    public CirnoFrostedIce(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(world.getRandom(), 60, 120));
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((random.nextInt(1) == 0 || this.canMelt(world, pos, 4)) && this.increaseAge(state, world, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            Direction[] var6 = Direction.values();

            for (Direction direction : var6) {
                mutable.set(pos, direction);
                BlockState blockState = world.getBlockState(mutable);
                if (blockState.isOf(this) && !this.increaseAge(blockState, world, mutable)) {
                    world.scheduleBlockTick(mutable, this, MathHelper.nextInt(random, 20, 40));
                }
            }

        } else {
            world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, 20, 40));
        }
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int i = state.get(AGE);
        if (i < 3) {
            world.setBlockState(pos, state.with(AGE, i + 1), 2);
            return false;
        } else {
            this.melt(state, world, pos);
            return true;
        }
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (sourceBlock.getDefaultState().isOf(this) && this.canMelt(world, pos, 2)) {
            this.melt(state, world, pos);
        }

        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    private boolean canMelt(BlockView world, BlockPos pos, int maxNeighbors) {
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction[] var6 = Direction.values();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            mutable.set(pos, direction);
            if (world.getBlockState(mutable).isOf(this)) {
                ++i;
                if (i >= maxNeighbors) {
                    return false;
                }
            }
        }

        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}