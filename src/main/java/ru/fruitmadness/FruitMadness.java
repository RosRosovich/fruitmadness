package ru.fruitmadness;

import net.fabricmc.api.ModInitializer;
import ru.fruitmadness.registry.*;
import ru.fruitmadness.worldgen.MangoWorldGen;

public class FruitMadness implements ModInitializer {

    public static final String MOD_ID = "fruitmadness";

    @Override
    public void onInitialize() {

        ModEffects.register();
        ModBlocks.register();
        ModEntities.register();
        ModItems.register();
        ModItemGroups.register();
        ModTrades.register();
        ModRecipes.register();
        ModFlammableBlocks.register();
        ModFuelItems.register();
        ModLootTables.register();
        MangoWorldGen.register();
    }
}