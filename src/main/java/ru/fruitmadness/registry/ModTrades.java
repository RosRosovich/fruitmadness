package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public final class ModTrades {

    public static void register() {
        // === Странствующий торговец ===
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add((entity, random) ->
                    new TradeOffer(
                            new TradedItem(Items.EMERALD, 1),
                            new ItemStack(ModItems.MANGO),
                            12,
                            1,
                            0.05f
                    )
            );

            factories.add((entity, random) ->
                    new TradeOffer(
                            new TradedItem(Items.EMERALD, 2),
                            new ItemStack(ModBlocks.MANGO_LOG, 16),
                            8,
                            1,
                            0.05f
                    )
            );

            factories.add((entity, random) ->
                    new TradeOffer(
                            new TradedItem(Items.EMERALD, 3),
                            new ItemStack(ModBlocks.MANGO_LEAVES, 16),
                            8,
                            1,
                            0.05f
                    )
            );
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 5, factories -> {
            factories.add((entity, random) ->
                    new TradeOffer(
                            new TradedItem(Items.EMERALD, 18),
                            new ItemStack(ModItems.LEEK),
                            1,     
                            30,
                            0.1f
                    )
            );
        });
    }
}