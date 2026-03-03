package ru.fruitmadness.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;

public class MangoItem extends Item {

    public MangoItem() {
        super(new Item.Settings()
                .component(
                        DataComponentTypes.FOOD,
                        new FoodComponent.Builder()
                                .nutrition(4)
                                .saturationModifier(0.4f)
                                .build()
                )
        );
    }
}
