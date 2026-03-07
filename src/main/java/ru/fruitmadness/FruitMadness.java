package ru.fruitmadness;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import ru.fruitmadness.entity.SharkEntity;
import ru.fruitmadness.registry.*;
import ru.fruitmadness.world.SharkSpawns;
import ru.fruitmadness.worldgen.MangoWorldGen;

public class FruitMadness implements ModInitializer {

    public static final String MOD_ID = "fruitmadness";

    @Override
    public void onInitialize() {
        ModEffects.register();
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();
        ModItemGroups.register();
        ModStrippableBlocks.register();
        ModTrades.register();
        ModRecipes.register();
        ModFlammableBlocks.register();
        ModFuelItems.register();
        ModLootTables.register();
        MangoWorldGen.register();
        SharkSpawns.register();

        FabricDefaultAttributeRegistry.register(ModEntities.SHARK, SharkEntity.createSharkAttributes());
    }
}