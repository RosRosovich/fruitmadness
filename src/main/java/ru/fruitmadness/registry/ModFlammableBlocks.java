package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public final class ModFlammableBlocks {

    public static void register() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();
        registry.add(ModBlocks.MANGO_LOG, 5, 5);
        registry.add(ModBlocks.MANGO_PLANKS, 20, 5);
        registry.add(ModBlocks.MANGO_LEAVES, 60, 30);
        registry.add(ModBlocks.MANGO_SLAB, 20, 5);
        registry.add(ModBlocks.MANGO_STAIRS, 20, 5);
        registry.add(ModBlocks.MANGO_FENCE, 20, 5);
        registry.add(ModBlocks.MANGO_FENCE_GATE, 20, 5);
        registry.add(ModBlocks.MANGO_BUTTON, 20, 5);
        registry.add(ModBlocks.MANGO_PRESSURE_PLATE, 20, 5);
        registry.add(ModBlocks.MANGO_SAPLING, 60, 100);
    }
}