package ru.fruitmadness.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import ru.fruitmadness.registry.ModEffects;
import ru.fruitmadness.registry.ModItems;

public class GoldenMangoItem extends Item {

    public GoldenMangoItem() {
        super(new Item.Settings()
                .component(
                        DataComponentTypes.FOOD,
                        new FoodComponent.Builder()
                                .nutrition(6)
                                .saturationModifier(1.2f)
                                .alwaysEdible()
                                .statusEffect(
                                        new StatusEffectInstance(
                                                Registries.STATUS_EFFECT.getEntry(StatusEffects.FIRE_RESISTANCE.value()),
                                                20 * 60,
                                                1
                                        ),
                                        1.0f
                                )
                                .statusEffect(
                                        new StatusEffectInstance(
                                                getBlessingEntry(),
                                                20 * 30,
                                                0
                                        ),
                                        1.0f
                                )
                                .build()
                )
        );
    }

    private static RegistryEntry<StatusEffect> getBlessingEntry() {
        return Registries.STATUS_EFFECT.getEntry(ModEffects.BLESSING);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);

        if (!world.isClient() && user instanceof PlayerEntity player) {
            ServerWorld serverWorld = (ServerWorld) world;
            Random random = serverWorld.getRandom();

            int count = 1 + random.nextInt(2);

            for (int i = 0; i < count; i++) {
                ItemStack pitStack = new ItemStack(ModItems.GOLDEN_PIT);

                double offsetX = (random.nextDouble() - 0.5) * 2.0;
                double offsetZ = (random.nextDouble() - 0.5) * 2.0;

                ItemEntity itemEntity = new ItemEntity(
                        serverWorld,
                        player.getX() + offsetX,
                        player.getY() + 0.5,
                        player.getZ() + offsetZ,
                        pitStack
                );

                itemEntity.setVelocity(
                        (random.nextDouble() - 0.5) * 0.1,
                        0.15,
                        (random.nextDouble() - 0.5) * 0.1
                );

                itemEntity.setNeverDespawn();

                serverWorld.spawnEntity(itemEntity);
            }
        }

        return result;
    }
}