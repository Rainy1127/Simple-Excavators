package net.teamsolar.simplest_excavators.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExcavatorItem extends DiggerItemWithoutDurability {
    public ExcavatorItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_SHOVEL, properties);
    }

    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, Player player) {
        List<BlockPos> positions = new ArrayList<>();


        if (player.isShiftKeyDown()) {
            return positions;
        }
        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        } else {
            // assuming that the clip hit a block
            switch (traceResult.getDirection()) {
                case UP, DOWN:
                    for (int x = -range; x <= range; x++) {
                        for (int y = -range; y <= range; y++) {
                            positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY(), initialBlockPos.getZ() + y));
                        }
                    }
                    break;
                case EAST, WEST:
                    for (int x = -range; x <= range; x++) {
                        for (int y = -range; y <= range; y++) {
                            positions.add(new BlockPos(initialBlockPos.getX(), initialBlockPos.getY() + y, initialBlockPos.getZ() + x));
                        }
                    }
                    break;
                case NORTH, SOUTH:
                    for (int x = -range; x <= range; x++) {
                        for (int y = -range; y <= range; y++) {
                            positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ()));
                        }
                    }
                    break;
            }
            positions = positions.stream().filter(it -> !it.equals(initialBlockPos)).toList();

            return positions;
        }
    }

    void useOnWithoutRecursion(UseOnContext context, Item originalItem) {
        // Does not return an interaction result, but passes the UseOnContext to requisite blocks when they are modified
        // Does nothing on air
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Player player = context.getPlayer();
        if(level.isEmptyBlock(blockpos)) {
            return;
        }
        ItemStack itemstack = context.getItemInHand();
        if(itemstack.getItem() != originalItem) {
            return;
        }
        BlockState intermediateBlockState = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
        BlockState finalBlockState = null;
        if (intermediateBlockState != null && level.getBlockState(blockpos.above()).isAir()) {
            level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            finalBlockState = intermediateBlockState;
        } else {
            intermediateBlockState = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false);
            if (intermediateBlockState != null) {
                if (!level.isClientSide()) {
                    level.levelEvent(null, 1009, blockpos, 0);
                }
                finalBlockState = intermediateBlockState;
            }
        }

        if (finalBlockState != null) {
            if (!level.isClientSide) {
                level.setBlock(blockpos, finalBlockState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, finalBlockState));
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = context.getPlayer();
            BlockState intermediateBlockState = blockState.getToolModifiedState(context, net.neoforged.neoforge.common.ItemAbilities.SHOVEL_FLATTEN, false);
            BlockState finalBlockState = null;
            if (intermediateBlockState != null && level.getBlockState(blockPos.above()).isAir()) {
                level.playSound(player, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                finalBlockState = intermediateBlockState;
            } else if ((finalBlockState = blockState.getToolModifiedState(context, net.neoforged.neoforge.common.ItemAbilities.SHOVEL_DOUSE, false)) != null) {
                if (!level.isClientSide()) {
                    level.levelEvent(null, 1009, blockPos, 0);
                }

            }
            if (finalBlockState != null) {
                if (!level.isClientSide) {
                    level.setBlock(blockPos, finalBlockState, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, finalBlockState));
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }

                if(player != null) {
                    for(BlockPos nextBlockPos: getBlocksToBeDestroyed(1, blockPos, player)) {
                        useOnWithoutRecursion(offsetContext(context, nextBlockPos.subtract(blockPos)), context.getItemInHand().getItem());
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }
    private UseOnContext offsetContext(UseOnContext context, Vec3i offset) {
        var offsetAsVec3 = Vec3.atCenterOf(offset);
        var newLocation = context.getClickLocation().add(offsetAsVec3);
        var newBlockPos = context.getClickedPos().offset(offset);
        return new UseOnContext(
                context.getLevel(),
                context.getPlayer(),
                context.getHand(),
                context.getItemInHand(),
                new BlockHitResult(
                        newLocation,
                        context.getClickedFace(),
                        newBlockPos,
                        context.isInside()
                )
        );
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }
}
