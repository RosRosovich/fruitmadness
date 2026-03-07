package ru.fruitmadness.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import ru.fruitmadness.FruitMadness;

import java.util.Optional;

public class MangoSaplingBlock extends SaplingBlock {

    private static final RegistryKey<ConfiguredFeature<?, ?>> MANGO_TREE_KEY =
            RegistryKey.of(
                    net.minecraft.registry.RegistryKeys.CONFIGURED_FEATURE,
                    Identifier.of(FruitMadness.MOD_ID, "mango_tree")
            );

    private static final RegistryKey<ConfiguredFeature<?, ?>> BIG_MANGO_TREE_KEY =
            RegistryKey.of(
                    net.minecraft.registry.RegistryKeys.CONFIGURED_FEATURE,
                    Identifier.of(FruitMadness.MOD_ID, "big_mango_tree")
            );

    private static final SaplingGenerator MANGO_GENERATOR =
            new SaplingGenerator(
                    "mango",
                    Optional.empty(),
                    Optional.of(MANGO_TREE_KEY),
                    Optional.of(BIG_MANGO_TREE_KEY)
            );

    public MangoSaplingBlock(Settings settings) {
        super(MANGO_GENERATOR, settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.GRASS_BLOCK)
                || floor.isOf(Blocks.DIRT)
                || floor.isOf(Blocks.COARSE_DIRT)
                || floor.isOf(Blocks.PODZOL)
                || floor.isOf(Blocks.MYCELIUM)
                || floor.isOf(Blocks.FARMLAND);
    }
}