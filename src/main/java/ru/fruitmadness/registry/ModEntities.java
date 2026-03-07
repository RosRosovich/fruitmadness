package ru.fruitmadness.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.entity.GoldenPitEntity;
import ru.fruitmadness.entity.SharkEntity;

public final class ModEntities {

    public static final EntityType<GoldenPitEntity> GOLDEN_PIT = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FruitMadness.MOD_ID, "golden_pit"),
            EntityType.Builder.<GoldenPitEntity>create(GoldenPitEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .build()
    );

    public static final EntityType<SharkEntity> SHARK = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(FruitMadness.MOD_ID, "shark"),
            EntityType.Builder.create(SharkEntity::new, SpawnGroup.WATER_CREATURE)
                    .dimensions(1.8f, 0.9f)
                    .eyeHeight(0.4f)
                    .build()
    );

    public static final Item SHARK_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of(FruitMadness.MOD_ID, "shark_spawn_egg"),
            new SpawnEggItem(SHARK, 0x2b4f6e, 0x9bb8d4, new Item.Settings())
    );

    public static void register() {
    }
}