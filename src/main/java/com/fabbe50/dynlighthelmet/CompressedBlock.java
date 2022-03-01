package com.fabbe50.dynlighthelmet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class CompressedBlock extends Block {
    public static final IntProperty COMPRESSION = IntProperty.of("compression", 0, 9);
    public CompressedBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(COMPRESSION, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COMPRESSION);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return super.getPickStack(world, pos, state);
    }
}
