package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import ru.fruitmadness.registry.ModBlocks;

public final class ModComposter {

    public static void register() {
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.MANGO_LEAVES.asItem(), 0.30F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.MANGO_SAPLING.asItem(), 0.30F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MANGO, 0.65F);
    }
}