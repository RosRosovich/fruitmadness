package ru.fruitmadness.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BiomeTags;
import ru.fruitmadness.registry.ModEntities;

public class SharkSpawns {

    public static void register() {
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OCEAN),
                SpawnGroup.WATER_CREATURE,
                ModEntities.SHARK,
                5,
                1,
                2
        );
    }
}