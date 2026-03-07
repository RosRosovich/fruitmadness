package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;

public class ModLootTables {
    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) {
                return;
            }

            Identifier id = key.getValue();

             if (id.equals(LootTables.JUNGLE_TEMPLE_CHEST.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));
                poolBuilder.with(ItemEntry.builder(ModItems.BLOWGUN)
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
                poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (id.equals(LootTables.DESERT_PYRAMID_CHEST.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));
                poolBuilder.with(ItemEntry.builder(ModItems.BLOWGUN)
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))));
                poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_PIT)
                        .conditionally(RandomChanceLootCondition.builder(0.3f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 6))));
                tableBuilder.pool(poolBuilder.build());
            }
            else if (id.equals(LootTables.JUNGLE_TEMPLE_DISPENSER_CHEST.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1));
                poolBuilder.with(ItemEntry.builder(ModItems.MANGO)
                        .conditionally(RandomChanceLootCondition.builder(0.6f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}