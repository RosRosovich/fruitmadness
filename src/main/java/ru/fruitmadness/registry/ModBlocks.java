package ru.fruitmadness.registry;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.block.*;

public final class ModBlocks {

    public static final Block MANGO_LOG = new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block STRIPPED_MANGO_LOG = new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_PLANKS = new Block(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_SLAB = new SlabBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_STAIRS = new StairsBlock(
            ModBlocks.MANGO_PLANKS.getDefaultState(),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_FENCE = new FenceBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_FENCE_GATE = new FenceGateBlock(
            WoodType.OAK,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_BUTTON = new ButtonBlock(
            BlockSetType.OAK,
            30,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .noCollision()
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_PRESSURE_PLATE = new PressurePlateBlock(
            BlockSetType.OAK,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block MANGO_LEAVES = new Block(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::canSpawnOnLeaves)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)
                    .burnable()
    );

    public static final Block MANGO_SAPLING = new MangoSaplingBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .strength(0.0F)
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .ticksRandomly()
                    .burnable()
    );

    public static final Block MANGO_DOOR = new DoorBlock(
            BlockSetType.OAK,
            AbstractBlock.Settings.copy(Blocks.OAK_DOOR).strength(3.0F)
                    .nonOpaque()
    );

    public static final Block MANGO_TRAPDOOR = new TrapdoorBlock(
            BlockSetType.OAK,
            AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).strength(3.0F)
                    .nonOpaque()
    );

    public static final Block MANGO_BLOCK = new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static final Block STRIPPED_MANGO_BLOCK = new PillarBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()
    );

    public static void register() {
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_log"), MANGO_LOG);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "stripped_mango_log"), STRIPPED_MANGO_LOG);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_planks"), MANGO_PLANKS);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_slab"), MANGO_SLAB);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_stairs"), MANGO_STAIRS);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_fence"), MANGO_FENCE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_fence_gate"), MANGO_FENCE_GATE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_button"), MANGO_BUTTON);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_pressure_plate"), MANGO_PRESSURE_PLATE);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_leaves"), MANGO_LEAVES);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_sapling"), MANGO_SAPLING);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_block"), MANGO_BLOCK);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "stripped_mango_block"), STRIPPED_MANGO_BLOCK);

        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_door"), MANGO_DOOR);
        Registry.register(Registries.BLOCK, Identifier.of(FruitMadness.MOD_ID, "mango_trapdoor"), MANGO_TRAPDOOR);
    }
}