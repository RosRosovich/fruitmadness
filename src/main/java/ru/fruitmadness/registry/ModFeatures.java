package ru.fruitmadness.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.worldgen.feature.MangoTreeFeature;

public final class ModFeatures {

    public static final Feature<DefaultFeatureConfig> MANGO_TREE =
            new MangoTreeFeature(DefaultFeatureConfig.CODEC);

    public static void register() {
        Registry.register(
                Registries.FEATURE,
                Identifier.of(FruitMadness.MOD_ID, "mango_tree"),
                MANGO_TREE
        );
    }
}