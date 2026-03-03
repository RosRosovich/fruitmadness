package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public final class ModFuelItems {

    public static void register() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(ModItems.MANGO_LOG_ITEM, 300);
        registry.add(ModItems.MANGO_PLANKS_ITEM, 300);
        registry.add(ModItems.MANGO_SAPLING_ITEM, 100);
        registry.add(ModItems.MANGO_SLAB_ITEM, 150);
        registry.add(ModItems.MANGO_STAIRS_ITEM, 300);
        registry.add(ModItems.MANGO_FENCE_ITEM, 300);
        registry.add(ModItems.MANGO_FENCE_GATE_ITEM, 300);
        registry.add(ModItems.MANGO_BUTTON_ITEM, 100);
        registry.add(ModItems.MANGO_PRESSURE_PLATE_ITEM, 300);
    }
}