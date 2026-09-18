package ru.fruitmadness.item;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class LeekItem extends SwordItem {

    private static final float SWEEP_BASE_DAMAGE = 3.0f;
    private static final float SWEEP_RATIO = 1.0f;
    private static final double SWEEP_RADIUS = 3.5;

    private static final int SLAM_COOLDOWN = 100;
    private static final double SLAM_RADIUS = 5.0;
    private static final float SLAM_DAMAGE = 3.0f;

    private static final float ATTACK_DAMAGE_MODIFIER = -2.0f;
    private static final float ATTACK_SPEED = 0.8f;

    private static final Identifier NUCLEAR_CHAPALAKH_ID =
            Identifier.of("fruitmadness", "combat/nuclear_chapalakh");

    public LeekItem() {
        super(
                ToolMaterials.IRON,
                new Item.Settings()
                        .maxCount(1)
                        .maxDamage(1200)
                        .attributeModifiers(SwordItem.createAttributeModifiers(
                                ToolMaterials.NETHERITE,
                                (int) ATTACK_DAMAGE_MODIFIER,
                                ATTACK_SPEED - 3.2f
                        ))
        );
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.postHit(stack, target, attacker);

        if (attacker instanceof PlayerEntity player && !player.getWorld().isClient()) {
            World world = player.getWorld();
            float sweepDamage = SWEEP_BASE_DAMAGE * SWEEP_RATIO;

            Vec3d center = target.getPos();
            Box box = new Box(
                    center.x - SWEEP_RADIUS, center.y - SWEEP_RADIUS, center.z - SWEEP_RADIUS,
                    center.x + SWEEP_RADIUS, center.y + SWEEP_RADIUS, center.z + SWEEP_RADIUS
            );

            List<Entity> nearby = world.getOtherEntities(player, box);

            for (Entity entity : nearby) {
                if (entity == target) continue;

                if (entity instanceof LivingEntity living && entity != player) {
                    living.damage(player.getDamageSources().playerAttack(player), sweepDamage);

                    double dx = living.getX() - player.getX();
                    double dz = living.getZ() - player.getZ();
                    double len = Math.sqrt(dx * dx + dz * dz);
                    if (len > 0.01) {
                        living.addVelocity(dx / len * 0.4, 0.1, dz / len * 0.4);
                        living.velocityModified = true;
                    }
                }
            }

            if (world instanceof ServerWorld serverWorld) {
                Vec3d look = player.getRotationVec(1.0f);
                Vec3d origin = player.getPos().add(0, player.getStandingEyeHeight() * 0.6, 0);

                for (int i = 0; i < 12; i++) {
                    double angle = Math.toRadians(-60 + i * 10);
                    double baseAngle = Math.atan2(look.z, look.x);
                    double px = origin.x + Math.cos(baseAngle + angle) * 2.0;
                    double pz = origin.z + Math.sin(baseAngle + angle) * 2.0;
                    serverWorld.spawnParticles(ParticleTypes.SWEEP_ATTACK,
                            px, origin.y, pz,
                            1, 0, 0, 0, 0);
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        return result;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.pass(stack);
        }

        if (!world.isClient()) {
            Vec3d center = user.getPos();

            Box box = new Box(
                    center.x - SLAM_RADIUS, center.y - 2, center.z - SLAM_RADIUS,
                    center.x + SLAM_RADIUS, center.y + 3, center.z + SLAM_RADIUS
            );

            List<Entity> entities = world.getOtherEntities(user, box);

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity living && entity != user) {
                    living.damage(user.getDamageSources().playerAttack(user), SLAM_DAMAGE);

                    Vec3d direction = living.getPos().subtract(user.getPos()).normalize();
                    double knockbackStrength = 2.0;
                    living.setVelocity(
                            direction.x * knockbackStrength,
                            0.6,
                            direction.z * knockbackStrength
                    );
                    living.velocityModified = true;
                }
            }

            if (world instanceof ServerWorld serverWorld) {
                for (int i = 0; i < 40; i++) {
                    double angle = Math.toRadians(i * 9);
                    double radius = 0.5 + (i % 5) * 0.5;
                    double px = center.x + Math.cos(angle) * radius;
                    double pz = center.z + Math.sin(angle) * radius;
                    serverWorld.spawnParticles(ParticleTypes.CLOUD,
                            px, center.y + 0.1, pz,
                            1, 0, 0, 0, 0.05);
                }
                serverWorld.spawnParticles(ParticleTypes.COMPOSTER,
                        center.x, center.y + 0.3, center.z,
                        8, 0.5, 0.2, 0.5, 0.02);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.BLOCK_CROP_BREAK, SoundCategory.PLAYERS, 1.0f, 0.6f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.BLOCK_BAMBOO_BREAK, SoundCategory.PLAYERS, 1.0f, 0.7f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 0.6f, 0.6f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.8f, 0.9f);

            if (user instanceof ServerPlayerEntity serverPlayer) {
                AdvancementEntry advancement = serverPlayer.getServer()
                        .getAdvancementLoader()
                        .get(NUCLEAR_CHAPALAKH_ID);

                if (advancement != null) {
                    var progress = serverPlayer.getAdvancementTracker().getProgress(advancement);
                    if (!progress.isDone()) {
                        for (String criterion : progress.getUnobtainedCriteria()) {
                            serverPlayer.getAdvancementTracker().grantCriterion(advancement, criterion);
                        }
                    }
                }
            }

            stack.damage(4, user, LivingEntity.getSlotForHand(user.getActiveHand()));
        }

        user.getItemCooldownManager().set(this, SLAM_COOLDOWN);
        user.swingHand(user.getActiveHand());
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.BONE_MEAL);
    }
}