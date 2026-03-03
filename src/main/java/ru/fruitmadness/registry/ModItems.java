package ru.fruitmadness.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.item.GoldenMangoItem;
import ru.fruitmadness.item.GoldenPitItem;
import ru.fruitmadness.item.MangoItem;
import ru.fruitmadness.item.BlowgunItem;

public final class ModItems {

    // Еда
    public static final Item MANGO = new MangoItem();
    public static final Item GOLDEN_MANGO = new GoldenMangoItem();
    public static final Item GOLDEN_PIT = new GoldenPitItem();

    // Блоки
    public static final Item MANGO_LOG_ITEM = new BlockItem(ModBlocks.MANGO_LOG, new Item.Settings());
    public static final Item MANGO_PLANKS_ITEM = new BlockItem(ModBlocks.MANGO_PLANKS, new Item.Settings());
    public static final Item MANGO_SAPLING_ITEM = new BlockItem(ModBlocks.MANGO_SAPLING, new Item.Settings());
    public static final Item MANGO_SLAB_ITEM = new BlockItem(ModBlocks.MANGO_SLAB, new Item.Settings());
    public static final Item MANGO_STAIRS_ITEM = new BlockItem(ModBlocks.MANGO_STAIRS, new Item.Settings());
    public static final Item MANGO_FENCE_ITEM = new BlockItem(ModBlocks.MANGO_FENCE, new Item.Settings());
    public static final Item MANGO_FENCE_GATE_ITEM = new BlockItem(ModBlocks.MANGO_FENCE_GATE, new Item.Settings());
    public static final Item MANGO_BUTTON_ITEM = new BlockItem(ModBlocks.MANGO_BUTTON, new Item.Settings());
    public static final Item MANGO_PRESSURE_PLATE_ITEM = new BlockItem(ModBlocks.MANGO_PRESSURE_PLATE, new Item.Settings());
    public static final Item MANGO_LEAVES_ITEM = new BlockItem(ModBlocks.MANGO_LEAVES, new Item.Settings());
    public static final Item BLOWGUN = new BlowgunItem(new Item.Settings().maxCount(1));
    public static void register() {
        // Еда
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango"), MANGO);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "golden_mango"), GOLDEN_MANGO);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "golden_pit"), GOLDEN_PIT);

        // Блоки
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_log"), MANGO_LOG_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_planks"), MANGO_PLANKS_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_sapling"), MANGO_SAPLING_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_slab"), MANGO_SLAB_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_stairs"), MANGO_STAIRS_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_fence"), MANGO_FENCE_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_fence_gate"), MANGO_FENCE_GATE_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_button"), MANGO_BUTTON_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_pressure_plate"), MANGO_PRESSURE_PLATE_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "mango_leaves"), MANGO_LEAVES_ITEM);
        Registry.register(Registries.ITEM, Identifier.of(FruitMadness.MOD_ID, "blowgun"), BLOWGUN);
    }
}