package ru.fruitmadness.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;

public final class ModItemGroups {

    public static final ItemGroup GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.fruitmadness"))
            .icon(() -> new ItemStack(ModItems.MANGO))
            .entries((displayContext, entries) -> {
                // Еда
                entries.add(ModItems.MANGO);
                entries.add(ModItems.GOLDEN_MANGO);
                entries.add(ModItems.GOLDEN_PIT);

                // Блоки
                entries.add(ModItems.MANGO_LOG_ITEM);
                entries.add(ModItems.MANGO_PLANKS_ITEM);
                entries.add(ModItems.MANGO_SAPLING_ITEM);
                entries.add(ModItems.MANGO_SLAB_ITEM);
                entries.add(ModItems.MANGO_STAIRS_ITEM);
                entries.add(ModItems.MANGO_FENCE_ITEM);
                entries.add(ModItems.MANGO_FENCE_GATE_ITEM);
                entries.add(ModItems.MANGO_BUTTON_ITEM);
                entries.add(ModItems.MANGO_PRESSURE_PLATE_ITEM);
                entries.add(ModItems.MANGO_LEAVES_ITEM);

                entries.add(ModItems.BLOWGUN);
            })
            .build();

    public static void register() {
        Registry.register(
                Registries.ITEM_GROUP,
                Identifier.of(FruitMadness.MOD_ID, "fruit_madness"),
                GROUP
        );
    }
}