package com.fabbe50.dynlighthelmet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class CompressedItemBlock extends BlockItem {
    public CompressedItemBlock(Block block, Settings settings) {
        super(block, settings);
    }

    /*@Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        return DynlightHelmet.COMPRESSED_COBBLED_DEEPSLATE.getDefaultState().with(CompressedBlock.COMPRESSION, getCompressionLevel(context.getStack()));
    }

    public static int getCompressionLevel(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt != null) {
            return nbt.getInt("compression");
        }
        return 0;
    }

    public static void setCompressionLevel(ItemStack stack, int compression) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("compression", compression);
    }*/
}
