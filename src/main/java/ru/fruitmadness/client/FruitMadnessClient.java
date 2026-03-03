package ru.fruitmadness.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import ru.fruitmadness.client.renderer.GoldenPitEntityRenderer;
import ru.fruitmadness.registry.ModBlocks;
import ru.fruitmadness.registry.ModColors;
import ru.fruitmadness.registry.ModEntities;
import ru.fruitmadness.registry.ModItems;

public class FruitMadnessClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Регистрируем рендерер для GoldenPitEntity
        EntityRendererRegistry.register(ModEntities.GOLDEN_PIT, GoldenPitEntityRenderer::new);

        // Рендер-слои для блоков
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_FENCE_GATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_PRESSURE_PLATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_SAPLING, RenderLayer.getCutout());

        // Цвета для листьев
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return ModColors.DEFAULT_FOLIAGE_COLOR;
            }
            return BiomeColors.getFoliageColor(world, pos);
        }, ModBlocks.MANGO_LEAVES);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ModColors.DEFAULT_FOLIAGE_COLOR, ModItems.MANGO_LEAVES_ITEM);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ModColors.DEFAULT_FOLIAGE_COLOR, ModItems.MANGO_SAPLING_ITEM);
    }
}