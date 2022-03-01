package com.fabbe50.dynlighthelmet;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class PotassiumChlorideFireBlock extends AbstractFireBlock {
    public PotassiumChlorideFireBlock(Settings settings) {
        super(settings, 3.0f);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return PotassiumChlorideFireBlock.isPotassiumChlorideBase(world.getBlockState(pos.down()));
    }

    public static boolean isPotassiumChlorideBase(BlockState state) {
        return state.isIn(DynlightHelmet.POTASSIUM_CHLORIDE_FIRE_BLOCKS);
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }
}
