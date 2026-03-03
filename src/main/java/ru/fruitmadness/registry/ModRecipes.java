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
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_MANGO, 1)
                .pattern("GGG")
                .pattern("GMG")
                .pattern("GGG")
                .input('G', Items.GOLD_INGOT)
                .input('M', ModItems.MANGO)
                .criterion("has_gold_ingot", conditionsFromItem(Items.GOLD_INGOT))
                .criterion("has_mango", conditionsFromItem(ModItems.MANGO))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_PLANKS_ITEM, 4)
                .input(ModItems.MANGO_LOG_ITEM)
                .criterion("has_mango_log", conditionsFromItem(ModItems.MANGO_LOG_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK, 4)
                .pattern("#")
                .pattern("#")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CRAFTING_TABLE, 1)
                .pattern("##")
                .pattern("##")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHEST, 1)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_SLAB_ITEM, 6)
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Ступеньки из досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MANGO_STAIRS_ITEM, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Забор из досок и палок
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModItems.MANGO_FENCE_ITEM, 3)
                .pattern("#S#")
                .pattern("#S#")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .input('S', Items.STICK)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Калитка из палок и досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_FENCE_GATE_ITEM, 1)
                .pattern("S#S")
                .pattern("S#S")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .input('S', Items.STICK)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Кнопка из досок
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_BUTTON_ITEM, 1)
                .input(ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);

        // Нажимная плита из досок
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MANGO_PRESSURE_PLATE_ITEM, 1)
                .pattern("##")
                .input('#', ModItems.MANGO_PLANKS_ITEM)
                .criterion("has_mango_planks", conditionsFromItem(ModItems.MANGO_PLANKS_ITEM))
                .offerTo(exporter);
    
    }
}