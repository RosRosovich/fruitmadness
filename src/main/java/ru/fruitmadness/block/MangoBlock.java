package ru.fruitmadness.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.fruitmadness.registry.ModBlocks;

public class MangoBlock extends Block {
    public MangoBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(player.getActiveHand());

        if (stack.getItem() == Items.NETHERITE_AXE ||
                stack.getItem() == Items.DIAMOND_AXE ||
                stack.getItem() == Items.IRON_AXE ||
                stack.getItem() == Items.GOLDEN_AXE ||
                stack.getItem() == Items.STONE_AXE ||
                stack.getItem() == Items.WOODEN_AXE) {

            if (!world.isClient) {
                world.setBlockState(pos, ModBlocks.STRIPPED_MANGO_BLOCK.getDefaultState());

                world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

                stack.damage(1, player, player.getSlotForHand(player.getActiveHand()));
            }
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }
}