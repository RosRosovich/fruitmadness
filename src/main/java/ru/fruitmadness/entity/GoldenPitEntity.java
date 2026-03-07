package ru.fruitmadness.entity;

import net.minecraft.block.Block;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.fruitmadness.registry.ModEntities;
import ru.fruitmadness.registry.ModItems;

public class GoldenPitEntity extends ThrownItemEntity {

    private static final TrackedData<Boolean> IS_LARGE =
            DataTracker.registerData(GoldenPitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private float enhancedDamage = 1.0f;

    public GoldenPitEntity(EntityType<? extends GoldenPitEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_LARGE, false); // По умолчанию маленький (только 1 блок)
    }

    public void setLarge(boolean large) {
        this.dataTracker.set(IS_LARGE, large);
    }

    public boolean isLarge() {
        return this.dataTracker.get(IS_LARGE);
    }

    public void setEnhancedDamage(float damage) {
        this.enhancedDamage = damage;
    }

    public float getEnhancedDamage() {
        return this.enhancedDamage;
    }

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

            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                entityHit.getEntity().setOnFireFor(6);

                if (isLarge()) {
                    setFireArea(world, entityHit.getEntity().getBlockPos(), 1); // Радиус 1 = область 3x3x3
                } else {
                    setFireAt(world, entityHit.getEntity().getBlockPos()); // Только 1 блок
                }

                world.addParticle(ParticleTypes.FLAME,
                        entityHit.getPos().x, entityHit.getPos().y, entityHit.getPos().z,
                        0, 0, 0);

            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());

                if (isLarge()) {
                    setFireArea(world, pos, 1); // Радиус 1 = область 3x3x3
                } else {
                    setFireAt(world, pos); // Только 1 блок
                }
            }

            this.discard();
        }
    }

    private void setFireAt(World world, BlockPos pos) {
        if (canIgniteBlock(world.getBlockState(pos)) && world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());

            world.addParticle(ParticleTypes.FLAME,
                    pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5,
                    0, 0.1, 0);
        }
    }

    private void setFireArea(World world, BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos firePos = center.add(x, y, z);

                    if (canIgniteBlock(world.getBlockState(firePos)) && world.getBlockState(firePos).isAir()) {
                        world.setBlockState(firePos, Blocks.FIRE.getDefaultState());

                        for (int i = 0; i < 2; i++) {
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
        Block block = blockState.getBlock();

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