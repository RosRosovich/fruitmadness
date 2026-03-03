package ru.fruitmadness.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.fruitmadness.FruitMadness;
import ru.fruitmadness.effect.BlessingEffect;

public final class ModEffects {

    public static final StatusEffect BLESSING = new BlessingEffect();

    public static void register() {
        Registry.register(
                Registries.STATUS_EFFECT,
                Identifier.of(FruitMadness.MOD_ID, "blessing"),
                BLESSING
        );
    }
}