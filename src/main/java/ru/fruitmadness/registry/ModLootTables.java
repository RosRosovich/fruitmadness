package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) {
                return;
            }

            Identifier id = key.getValue();

            // 1. Лут для листьев манго (саженец и манго)
            if (id.equals(Identifier.of("mangomadness", "blocks/mango_leaves"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));

                // Саженец с шансом 5%
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO_SAPLING_ITEM)
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));

                // Манго с шансом 2.5%
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.025f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));

                tableBuilder.pool(poolBuilder.build());
            }

            // 2. Сундуки джунглей
            else if (id.equals(Identifier.of("minecraft", "chests/jungle_temple"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));

                // Духовая трубка с шансом 15%
                poolBuilder.with(ItemEntry.builder(ModItems.BLOWGUN)
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));

                // Манго с шансом 40% (1-3 штуки)
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));

                // Золотые манго с шансом 20% (1-2 штуки)
                poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))));

                tableBuilder.pool(poolBuilder.build());
            }

            // 3. Сундуки пустынных храмов
            else if (id.equals(Identifier.of("minecraft", "chests/desert_pyramid"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));

                // Духовая трубка с шансом 20%
                poolBuilder.with(ItemEntry.builder(ModItems.BLOWGUN)
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));

                // Манго с шансом 50% (2-4 штуки)
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))));

                // Золотые косточки с шансом 30% (3-6 штук)
                poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_PIT)
                        .conditionally(RandomChanceLootCondition.builder(0.3f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 6))));

                tableBuilder.pool(poolBuilder.build());
            }

            // 4. Сундуки обычных джунглей
            else if (id.equals(Identifier.of("minecraft", "chests/jungle_temple_dispenser"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));

                // Манго с шансом 60% (1-4 штуки)
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.6f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))));

                tableBuilder.pool(poolBuilder.build());
            }

            // 5. Лут для всех остальных блоков манго (по 1 штуке)
            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_log"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_LOG_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_planks"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_PLANKS_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_slab"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_SLAB_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_stairs"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_STAIRS_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_fence"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_FENCE_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_fence_gate"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_FENCE_GATE_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_button"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_BUTTON_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_pressure_plate"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_PRESSURE_PLATE_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }

            else if (id.equals(Identifier.of("mangomadness", "blocks/mango_sapling"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.MANGO_SAPLING_ITEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}