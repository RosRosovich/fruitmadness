package ru.fruitmadness.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import ru.fruitmadness.FruitMadness;

public class MangoWorldGen {

    public static final RegistryKey<PlacedFeature> MANGO_TREE_PLACED_KEY =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                    Identifier.of(FruitMadness.MOD_ID, "mango_tree_placed"));

    public static void register() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        net.minecraft.registry.RegistryKey.of(
                                net.minecraft.registry.RegistryKeys.BIOME,
                                net.minecraft.util.Identifier.of("minecraft", "desert")
                        )
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MANGO_TREE_PLACED_KEY
        );
    }
}