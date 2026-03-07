package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import ru.fruitmadness.FruitMadness;

public class ModStrippableBlocks {
    public static void register() {
        StrippableBlockRegistry.register(
                ModBlocks.MANGO_LOG,
                ModBlocks.STRIPPED_MANGO_LOG
        );

        StrippableBlockRegistry.register(
                ModBlocks.MANGO_BLOCK,
                ModBlocks.STRIPPED_MANGO_BLOCK
        );
    }
}