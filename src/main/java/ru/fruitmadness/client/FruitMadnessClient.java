package ru.fruitmadness.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import ru.fruitmadness.client.model.BabySharkEntityModel;
import ru.fruitmadness.client.model.SharkEntityModel;
import ru.fruitmadness.client.renderer.GoldenPitEntityRenderer;
import ru.fruitmadness.client.renderer.SharkEntityRenderer;
import ru.fruitmadness.registry.ModBlocks;
import ru.fruitmadness.registry.ModColors;
import ru.fruitmadness.registry.ModEntities;
import ru.fruitmadness.registry.ModItems;

public class FruitMadnessClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.GOLDEN_PIT, GoldenPitEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHARK, SharkEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SharkEntityModel.SHARK_LAYER, SharkEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BabySharkEntityModel.BABY_SHARK_LAYER, BabySharkEntityModel::getTexturedModelData);

        ModelPredicateProviderRegistry.register(ModItems.BABY_SHARK_BUCKET, Identifier.of("filled"),
                (stack, world, entity, seed) -> 1.0f);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_FENCE_GATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_PRESSURE_PLATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_SAPLING, RenderLayer.getCutout());

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