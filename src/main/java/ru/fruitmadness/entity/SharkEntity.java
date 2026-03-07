package ru.fruitmadness.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import ru.fruitmadness.registry.ModItems;

import java.util.EnumSet;

public class SharkEntity extends WaterCreatureEntity {

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(SharkEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BABY =
            DataTracker.registerData(SharkEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int attackTimer = 0;

    public SharkEntity(EntityType<? extends WaterCreatureEntity> type, World world) {
        super(type, world);
        this.moveControl = new SharkMoveControl(this);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ATTACKING, false);
        builder.add(BABY, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBaby(nbt.getBoolean("Baby"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Baby", this.isBaby());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, EntityData entityData) {
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    public static DefaultAttributeContainer.Builder createSharkAttributes() {
        return createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8);
    }

    @Override
    protected void initGoals() {
        // Цели (targets)
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, SquidEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, DolphinEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, CodEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SalmonEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, TropicalFishEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, PufferfishEntity.class, true));

        // Поведение (goals)
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SharkAttackGoal(this, 2.0, true));
        this.goalSelector.add(2, new ChaseTargetGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 32.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new SwimAroundGoal(this, 1.5, 20));
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.dataTracker.set(ATTACKING, true);
        this.attackTimer = 10;

        float damage = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        Difficulty difficulty = this.getWorld().getDifficulty();
        if (difficulty == Difficulty.NORMAL) {
            damage = 8.0f;
        } else if (difficulty == Difficulty.HARD) {
            damage = 12.0f;
        }

        boolean attacked = target.damage(this.getDamageSources().mobAttack(this), damage);

        if (attacked && this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.DAMAGE_INDICATOR,
                    target.getX(), target.getY() + 0.5, target.getZ(),
                    10, 0.2, 0.2, 0.2, 0.2);

            if (target.isTouchingWater()) {
                serverWorld.spawnParticles(ParticleTypes.BUBBLE,
                        target.getX(), target.getY() + 0.5, target.getZ(),
                        20, 0.5, 0.5, 0.5, 0.1);
            }
        }

        return attacked;
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        super.onKilledOther(world, other);
        world.spawnParticles(
                ParticleTypes.CRIT,
                other.getX(), other.getBodyY(0.5), other.getZ(),
                30, 0.5, 0.5, 0.5, 0.2
        );
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (attackTimer > 0) {
            attackTimer--;
            if (attackTimer == 0) {
                this.dataTracker.set(ATTACKING, false);
            }
        }

        if (this.isBaby() && this.age > 24000) {
            this.setBaby(false);
        }

        if (!this.getWorld().isClient) {
            if (this.getY() > 58) {
                this.setVelocity(this.getVelocity().add(0, -0.03, 0));
            } else if (this.getY() < 45 && this.getVelocity().y < 0) {
                this.setVelocity(this.getVelocity().add(0, 0.02, 0));
            }

            if (!this.isSubmergedIn(FluidTags.WATER)) {
                this.setAir(this.getAir() - 1);

                if (this.getWorld().random.nextInt(10) == 0) {
                    this.setVelocity(
                            (this.getWorld().random.nextDouble() - 0.5) * 0.2,
                            0.1,
                            (this.getWorld().random.nextDouble() - 0.5) * 0.2
                    );

                    this.playSound(SoundEvents.ENTITY_COD_FLOP, 0.5f, 1.0f);

                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.SPLASH,
                                this.getX(), this.getY() + 0.5, this.getZ(),
                                5, 0.2, 0.2, 0.2, 0.1);
                    }
                }

                if (this.getAir() <= -20) {
                    this.setAir(0);
                    this.damage(this.getDamageSources().dryOut(), 1.0f);

                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.BUBBLE_POP,
                                this.getX(), this.getY() + 0.5, this.getZ(),
                                5, 0.1, 0.1, 0.1, 0);
                    }
                }
            } else {
                this.setAir(300);
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (this.isBaby() && stack.isOf(Items.WATER_BUCKET)) {
            if (!this.getWorld().isClient) {
                ItemStack bucketStack = new ItemStack(ModItems.BABY_SHARK_BUCKET);
                stack.decrement(1);
                player.setStackInHand(hand, bucketStack);
                this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
                this.discard();
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        if (this.isBaby() && stack.isOf(Items.TROPICAL_FISH)) {
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            this.setBaby(false);
            return ActionResult.success(this.getWorld().isClient);
        }

        return super.interactMob(player, hand);
    }

    public void setBaby(boolean baby) {
        this.dataTracker.set(BABY, baby);
        this.calculateDimensions();

        var healthAttr = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        var damageAttr = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        var speedAttr = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        var knockbackAttr = this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        var followRangeAttr = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);

        if (baby) {
            if (healthAttr != null) healthAttr.setBaseValue(25.0);
            if (damageAttr != null) damageAttr.setBaseValue(3.0);
            if (speedAttr != null) speedAttr.setBaseValue(1.5);
            if (knockbackAttr != null) knockbackAttr.setBaseValue(0.5);
            if (followRangeAttr != null) followRangeAttr.setBaseValue(16.0);
        } else {
            if (healthAttr != null) healthAttr.setBaseValue(50.0);
            if (damageAttr != null) damageAttr.setBaseValue(6.0);
            if (speedAttr != null) speedAttr.setBaseValue(2.0);
            if (knockbackAttr != null) knockbackAttr.setBaseValue(0.8);
            if (followRangeAttr != null) followRangeAttr.setBaseValue(32.0);
        }

        this.setHealth(this.getMaxHealth());
    }

    @Override
    public boolean isBaby() {
        return this.dataTracker.get(BABY);
    }

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_DOLPHIN_SWIM;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.isSubmergedIn(FluidTags.WATER)) {
            return SoundEvents.ENTITY_COD_FLOP;
        }
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    static class SharkMoveControl extends MoveControl {
        private final SharkEntity shark;

        public SharkMoveControl(SharkEntity shark) {
            super(shark);
            this.shark = shark;
        }

        @Override
        public void tick() {
            if (this.shark.isSubmergedIn(FluidTags.WATER)) {
                if (this.shark.getY() < 58) {
                    this.shark.setVelocity(this.shark.getVelocity().add(0.0, 0.005, 0.0));
                } else if (this.shark.getVelocity().y > 0) {
                    this.shark.setVelocity(this.shark.getVelocity().add(0.0, -0.01, 0.0));
                }
            }

            if (this.state == State.MOVE_TO && !this.shark.getNavigation().isIdle()) {
                double dx = this.targetX - this.shark.getX();
                double dy = this.targetY - this.shark.getY();
                double dz = this.targetZ - this.shark.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance < 1.0E-5) {
                    this.shark.setForwardSpeed(0.0f);
                    return;
                }

                float targetYaw = (float)(MathHelper.atan2(dz, dx) * 180.0 / Math.PI) - 90.0f;
                this.shark.setYaw(this.rotateYaw(this.shark.getYaw(), targetYaw, 15.0f));

                float speed = (float)(this.speed * this.shark.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                if (this.shark.getTarget() != null) {
                    speed *= 1.5f;
                }
                this.shark.setMovementSpeed(speed * 0.9f);

                if (distance > 0.0) {
                    double vy = dy / distance;
                    this.shark.setVelocity(this.shark.getVelocity().add(0.0, vy * 0.1, 0.0));
                }
            } else {
                this.shark.setForwardSpeed(0.0f);
                this.shark.setMovementSpeed(0.0f);
            }
        }

        private float rotateYaw(float currentYaw, float targetYaw, float maxSpeed) {
            float delta = MathHelper.wrapDegrees(targetYaw - currentYaw);
            if (delta > maxSpeed) {
                delta = maxSpeed;
            }
            if (delta < -maxSpeed) {
                delta = -maxSpeed;
            }
            return currentYaw + delta;
        }
    }

    static class ChaseTargetGoal extends Goal {
        private final SharkEntity shark;
        private LivingEntity target;
        private int cooldown;

        public ChaseTargetGoal(SharkEntity shark) {
            this.shark = shark;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            this.target = this.shark.getTarget();
            return target != null && target.isAlive() && this.shark.isSubmergedIn(FluidTags.WATER);
        }

        @Override
        public boolean shouldContinue() {
            return this.target != null && this.target.isAlive() && this.shark.isSubmergedIn(FluidTags.WATER)
                    && this.shark.squaredDistanceTo(this.target) < 1024.0;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
            this.target = null;
            this.shark.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (--this.cooldown <= 0) {
                this.cooldown = 10;
                double distance = this.shark.squaredDistanceTo(this.target);

                if (distance > 4.0) {
                    this.shark.getNavigation().startMovingTo(this.target, 2.0);
                } else {
                    this.shark.getLookControl().lookAt(this.target, 30.0f, 30.0f);
                    this.shark.getNavigation().stop();
                }
            }

            this.shark.getLookControl().lookAt(this.target, 30.0f, 30.0f);
        }
    }

    static class SharkAttackGoal extends MeleeAttackGoal {
        private final SharkEntity shark;

        public SharkAttackGoal(SharkEntity shark, double speed, boolean pauseWhenMobIdle) {
            super(shark, speed, pauseWhenMobIdle);
            this.shark = shark;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && this.shark.isSubmergedIn(FluidTags.WATER);
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.shark.isSubmergedIn(FluidTags.WATER);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.0;
        }
    }
}