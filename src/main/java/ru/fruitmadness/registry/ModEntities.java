package ru.fruitmadness.registry;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.entity.GoldenPitEntity;

public final class ModEntities {

    public static EntityType<GoldenPitEntity> GOLDEN_PIT;

    public static void register() {
        GOLDEN_PIT = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(FruitMadness.MOD_ID, "golden_pit"),
                EntityType.Builder.<GoldenPitEntity>create(GoldenPitEntity::new, SpawnGroup.MISC)
                        .makeFireImmune()  // Можно убрать если не нужно
                        .build(String.valueOf(EntityDimensions.fixed(0.25f, 0.25f)))  // Размеры передаем в build!
        );
    }
}