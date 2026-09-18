package ru.fruitmadness.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import ru.fruitmadness.registry.ModBlocks;

public class MangoTreeFeature extends Feature<DefaultFeatureConfig> {

    private static final int MIN_HEIGHT = 3;
    private static final int MAX_HEIGHT = 6;

    public MangoTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        BlockPos groundPos = origin.down();
        BlockState groundState = world.getBlockState(groundPos);
        if (!isValidGround(groundState)) {
            return false;
        }

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos checkPos = origin.add(dx, -1, dz);
                if (world.getBlockState(checkPos).isOf(Blocks.WATER)) {
                    return false;
                }
            }
        }

        int height = MIN_HEIGHT + random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);

        for (int i = 0; i <= height + 1; i++) {
            BlockPos checkPos = origin.up(i);
            if (!world.getBlockState(checkPos).isAir()) {
                return false;
            }
        }

        if (!world.getBlockState(groundPos).isOf(Blocks.DIRT)) {
            world.setBlockState(groundPos, Blocks.DIRT.getDefaultState(), 3);
        }

        for (int i = 0; i < height; i++) {
            world.setBlockState(origin.up(i), ModBlocks.MANGO_LOG.getDefaultState(), 3);
        }

        BlockPos topPos = origin.up(height);
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (Math.abs(dx) == 2 && Math.abs(dz) == 2) continue;
                    BlockPos leafPos = topPos.add(dx, dy, dz);
                    if (world.getBlockState(leafPos).isAir()) {
                        world.setBlockState(leafPos, ModBlocks.MANGO_LEAVES.getDefaultState(), 3);
                    }
                }
            }
        }

        BlockPos top = topPos.up(2);
        world.setBlockState(top, ModBlocks.MANGO_LEAVES.getDefaultState(), 3);
        world.setBlockState(top.north(), ModBlocks.MANGO_LEAVES.getDefaultState(), 3);
        world.setBlockState(top.south(), ModBlocks.MANGO_LEAVES.getDefaultState(), 3);
        world.setBlockState(top.east(), ModBlocks.MANGO_LEAVES.getDefaultState(), 3);
        world.setBlockState(top.west(), ModBlocks.MANGO_LEAVES.getDefaultState(), 3);

        return true;
    }

    private boolean isValidGround(BlockState state) {
        return state.isOf(Blocks.SAND)
                || state.isOf(Blocks.RED_SAND)
                || state.isOf(Blocks.DIRT)
                || state.isOf(Blocks.GRASS_BLOCK)
                || state.isOf(Blocks.COARSE_DIRT);
    }
}