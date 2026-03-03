package ru.fruitmadness.registry;

import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.block.MangoSaplingBlock;

public final class ModBlocks {

    // Основные блоки
    public static final Block MANGO_LOG = new PillarBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                    .strength(2.0F)
                    .burnable()
    );

    public static final Block MANGO_PLANKS = new Block(
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .burnable()
    );

    // Производные блоки из досок
    public static final Block MANGO_SLAB = new SlabBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_SLAB)
                    .strength(2.0F, 3.0F)
                    .burnable()
    );

    public static final Block MANGO_STAIRS = new StairsBlock(
            ModBlocks.MANGO_PLANKS.getDefaultState(),
            AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)
                    .strength(2.0F, 3.0F)
                    .burnable()
    );

    public static final Block MANGO_FENCE = new FenceBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_FENCE)
                    .strength(2.0F, 3.0F)
                    .burnable()
    );

    public static final Block MANGO_FENCE_GATE = new FenceGateBlock(
            WoodType.OAK,
            AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE)
                    .strength(2.0F, 3.0F)
                    .burnable()
    );

    public static final Block MANGO_BUTTON = new ButtonBlock(
            BlockSetType.OAK,
            30,
            AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)
                    .strength(0.5F)
                    .burnable()
    );

    public static final Block MANGO_PRESSURE_PLATE = new PressurePlateBlock(
            BlockSetType.OAK,
            AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)
                    .strength(0.5F)
                    .burnable()
    );

    public static final Block MANGO_LEAVES = new LeavesBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                    .strength(0.2F)
                    .nonOpaque()
                    .allowsSpawning(Blocks::canSpawnOnLeaves)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)
                    .burnable()
    ) {
        @Override
        public boolean hasRandomTicks(BlockState state) {
            return false; // Отключаем случайные обновления
        }

        @Override
        public void randomTick(BlockState state, net.minecraft.server.world.ServerWorld world,
                               net.minecraft.util.math.BlockPos pos, net.minecraft.util.math.random.Random random) {
            // Ничего не делаем - листья не опадают
        }
    };

    // Саженец
    public static final Block MANGO_SAPLING =
            new MangoSaplingBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)
                            .strength(0.0F)
                            .ticksRandomly()
                            .nonOpaque()
                            .burnable()
            );

    public static void register() {
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_log"), MANGO_LOG);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_planks"), MANGO_PLANKS);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_slab"), MANGO_SLAB);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_stairs"), MANGO_STAIRS);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_fence"), MANGO_FENCE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_fence_gate"), MANGO_FENCE_GATE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_button"), MANGO_BUTTON);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_pressure_plate"), MANGO_PRESSURE_PLATE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_leaves"), MANGO_LEAVES);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_sapling"), MANGO_SAPLING);
    }
}