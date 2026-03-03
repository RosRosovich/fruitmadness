package ru.fruitmadness.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ru.fruitmadness.entity.GoldenPitEntity;
import ru.fruitmadness.registry.ModItems;

public class BlowgunItem extends BowItem {

    public BlowgunItem(Settings settings) {
        super(settings.maxDamage(384));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Простая проверка: есть ли золотые косточки в инвентаре?
        if (!user.getAbilities().creativeMode && findGoldenPits(user).isEmpty()) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) {
            return;
        }

        int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
        float charge = getPullProgress(useTime);

        if (charge < 0.1f) {
            return;
        }

        boolean creative = player.getAbilities().creativeMode;

        if (!world.isClient) {
            // Создаем золотую косточку
            GoldenPitEntity goldenPit = GoldenPitEntity.create(world, player);

            // Устанавливаем скорость
            float speed = 3.0f * charge;
            goldenPit.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, speed, 1.0f);

            // Усиливаем урон (3.0 = 1.5 сердца) - косточка из духовой трубки сильнее
            goldenPit.setEnhancedDamage(3.0f);

            // Увеличиваем радиус поджога в 2 раза (4x4 вместо 2x2)
            goldenPit.setRadiusMultiplier(3.0f);

            world.spawnEntity(goldenPit);

            // Потребляем косточку, если не в творческом режиме
            if (!creative) {
                ItemStack pits = findGoldenPits(player);
                if (!pits.isEmpty()) {
                    pits.decrement(1);
                }
            }

            // Проигрываем звук выстрела
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                    1.0f, 1.5f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + charge * 0.5f);

            // Изнашиваем духовую трубку
            if (!creative) {
                stack.damage(1, player, LivingEntity.getSlotForHand(user.getActiveHand()));
            }
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    private ItemStack findGoldenPits(PlayerEntity player) {
        // Ищем золотые косточки в инвентаре
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(ModItems.GOLDEN_PIT)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static float getPullProgress(int useTicks) {
        float progress = (float)useTicks / 20.0f;
        progress = (progress * progress + progress * 2.0f) / 3.0f;
        return Math.min(progress, 1.0f);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.GOLD_INGOT);
    }
}