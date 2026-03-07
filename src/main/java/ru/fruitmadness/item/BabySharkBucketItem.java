package ru.fruitmadness.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import ru.fruitmadness.entity.SharkEntity;
import ru.fruitmadness.registry.ModEntities;

public class BabySharkBucketItem extends Item {

    public BabySharkBucketItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        BlockHitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.NONE);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos().offset(hitResult.getSide());

            if (!world.canPlayerModifyAt(player, pos)) {
                return TypedActionResult.pass(stack);
            }

            BlockState blockState = world.getBlockState(pos);
            if (blockState.isAir() || blockState.canBucketPlace(Fluids.WATER)) {

                if (world instanceof ServerWorld serverWorld) {
                    world.setBlockState(pos, Blocks.WATER.getDefaultState());

                    SharkEntity shark = ModEntities.SHARK.create(serverWorld);
                    if (shark != null) {
                        shark.setBaby(true);
                        shark.setPosition(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5);
                        serverWorld.spawnEntity(shark);

                        serverWorld.spawnParticles(ParticleTypes.SPLASH,
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                10, 0.3, 0.3, 0.3, 0.1);
                    }

                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                }

                player.incrementStat(Stats.USED.getOrCreateStat(this));

                if (!player.getAbilities().creativeMode) {
                    return TypedActionResult.success(
                            ItemUsage.exchangeStack(stack, player, Items.BUCKET.getDefaultStack()),
                            world.isClient()
                    );
                }

                return TypedActionResult.success(stack, world.isClient());
            }
        }

        return TypedActionResult.pass(stack);
    }
}