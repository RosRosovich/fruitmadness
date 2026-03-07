package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class ModRecipes extends FabricRecipeProvider {

    public ModRecipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void register() {
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Золотое манго
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_MANGO, 1)
                .pattern("GGG")
                .pattern("GMG")
                .pattern("GGG")
                .input('G', Items.GOLD_INGOT)
                .input('M', ModItems.MANGO)
                .criterion("has_gold_ingot", conditionsFromItem(Items.GOLD_INGOT))
                .criterion("has_mango", conditionsFromItem(ModItems.MANGO))
                .offerTo(exporter);

        // Доски из бревна
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_PLANKS_ITEM, 4)
                .input(ModItems.MANGO_LOG_ITEM)
                .criterion("has_mango_log", conditionsFromItem(ModItems.MANGO_LOG_ITEM))
                .offerTo(exporter);

        // Палки из досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK, 4)
                .pattern("#")
                .pattern("#")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Верстак из досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CRAFTING_TABLE, 1)
                .pattern("##")
                .pattern("##")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Сундук из досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHEST, 1)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Плита
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_SLAB_ITEM, 6)
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Ступеньки
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_STAIRS_ITEM, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Забор
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.MANGO_FENCE_ITEM, 3)
                .pattern("#S#")
                .pattern("#S#")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .input('S', Items.STICK)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Калитка
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_FENCE_GATE_ITEM, 1)
                .pattern("S#S")
                .pattern("S#S")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .input('S', Items.STICK)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Кнопка
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_BUTTON_ITEM, 1)
                .input(ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Нажимная плита
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_PRESSURE_PLATE_ITEM, 1)
                .pattern("##")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Манговая дверь
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_DOOR_ITEM, 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Манговый люк
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_TRAPDOOR_ITEM, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Манго блок
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_BLOCK_ITEM, 1)
                .pattern("LL")
                .pattern("LL")
                .input('L', ModItems.MANGO_LOG_ITEM)
                .criterion("has_mango_log", conditionsFromItem(ModItems.MANGO_LOG_ITEM))
                .offerTo(exporter);
    }
}