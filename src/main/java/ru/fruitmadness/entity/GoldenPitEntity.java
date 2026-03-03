package ru.fruitmadness.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import ru.fruitmadness.registry.ModEntities;
import ru.fruitmadness.registry.ModItems;

public class GoldenPitEntity extends ThrownItemEntity {

    private static final TrackedData<Integer> RADIUS_MULTIPLIER =
            DataTracker.registerData(GoldenPitEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private float enhancedDamage = 1.0f;

    // Конструктор для EntityType
    public GoldenPitEntity(EntityType<? extends GoldenPitEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RADIUS_MULTIPLIER, 10); // 1.0 * 10
    }

    public void setRadiusMultiplier(float multiplier) {
        this.dataTracker.set(RADIUS_MULTIPLIER, (int)(multiplier * 10));
    }

    public float getRadiusMultiplier() {
        return this.dataTracker.get(RADIUS_MULTIPLIER) / 10.0f;
    }

    public void setEnhancedDamage(float damage) {
        this.enhancedDamage = damage;
    }

    public float getEnhancedDamage() {
        return this.enhancedDamage;
    }

    // ВАЖНО: ВОЗВРАЩАЕМ СТАРЫЙ МЕТОД CREATE!
    public static GoldenPitEntity create(World world, LivingEntity owner) {
        GoldenPitEntity entity = new GoldenPitEntity(ModEntities.GOLDEN_PIT, world);
        entity.setOwner(owner);
        entity.setPosition(
                owner.getX(),
                owner.getEyeY() - 0.1,
                owner.getZ()
        );
        return entity;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient()) {
            World world = this.getWorld();
            float radiusMultiplier = getRadiusMultiplier();

            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                entityHit.getEntity().setOnFireFor(6); // Поджигаем на 6 секунд

                // Больший радиус поджога при усилении
                int radius = Math.round(1 * radiusMultiplier);
                setFireAround(world, entityHit.getEntity().getBlockPos(), radius);

                // Спавним частицы огня
                world.addParticle(ParticleTypes.FLAME,
                        entityHit.getPos().x, entityHit.getPos().y, entityHit.getPos().z,
                        0, 0, 0);

            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hitResult;

                // Больший радиус поджога при усилении
                int radius = Math.round(1 * radiusMultiplier);
                setFireAround(world, blockHit.getBlockPos().offset(blockHit.getSide()), radius);
            }

            this.discard();
        }
    }

    private void setFireAround(World world, net.minecraft.util.math.BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -1; y <= 1; y++) {
                    net.minecraft.util.math.BlockPos firePos = center.add(x, y, z);
                    BlockState blockState = world.getBlockState(firePos);

                    if (canIgniteBlock(blockState) && world.getBlockState(firePos).isAir()) {
                        world.setBlockState(firePos, Blocks.FIRE.getDefaultState());

                        // Спавним частицы огня
                        for (int i = 0; i < 3; i++) {
                            world.addParticle(ParticleTypes.FLAME,
                                    firePos.getX() + 0.5,
                                    firePos.getY() + 0.1,
                                    firePos.getZ() + 0.5,
                                    (world.random.nextDouble() - 0.5) * 0.2,
                                    0.1,
                                    (world.random.nextDouble() - 0.5) * 0.2);
                        }
                    }
                }
            }
        }
    }

    private boolean canIgniteBlock(BlockState blockState) {
        var block = blockState.getBlock();

        return block != Blocks.WATER && block != Blocks.LAVA &&
                block != Blocks.ICE && block != Blocks.PACKED_ICE &&
                block != Blocks.BLUE_ICE && block != Blocks.FROSTED_ICE &&
                block != Blocks.SNOW_BLOCK && block != Blocks.SNOW &&
                block != Blocks.FIRE && block != Blocks.SOUL_FIRE &&
                block != Blocks.OBSIDIAN && block != Blocks.CRYING_OBSIDIAN &&
                block != Blocks.NETHERITE_BLOCK && block != Blocks.ANCIENT_DEBRIS &&
                block != Blocks.NETHER_PORTAL && block != Blocks.END_PORTAL &&
                block != Blocks.END_PORTAL_FRAME;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GOLDEN_PIT;
    }
}