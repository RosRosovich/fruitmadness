package ru.fruitmadness.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import ru.fruitmadness.entity.GoldenPitEntity;

public final class BlessingEffect extends StatusEffect {

    public BlessingEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFD700);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 40 == 0; // Каждые 2 секунды
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player) || player.getWorld().isClient()) {
            return true;
        }

        ServerWorld world = (ServerWorld) player.getWorld();
        Vec3d playerPos = player.getPos();

        // Направления для выстрелов: 8 сторон
        float[][] directions = {
                {0, 0},      // вперед
                {180, 0},    // назад
                {-90, 0},    // влево
                {90, 0},     // вправо
                {-45, 0},    // вперед-влево
                {45, 0},     // вперед-вправо
                {-135, 0},   // назад-влево
                {135, 0}     // назад-вправо
        };

        for (float[] dir : directions) {
            float yaw = player.getYaw() + dir[0];
            float pitch = player.getPitch() + dir[1];

            GoldenPitEntity goldenPit = GoldenPitEntity.create(world, player);

            // Позиция на уровне туловища
            goldenPit.setPosition(
                    playerPos.x,
                    playerPos.y + 1.8, // уровень туловища
                    playerPos.z
            );

            // Задаем скорость
            float speed = 1.0f + amplifier * 0.2f;
            goldenPit.setVelocity(player, pitch, yaw, 0.0f, speed, 1.0f);

            world.spawnEntity(goldenPit);
        }

        return true;
    }
}