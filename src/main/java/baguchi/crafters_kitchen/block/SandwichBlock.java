package baguchi.crafters_kitchen.block;

import baguchi.crafters_kitchen.blockentity.SandwichBlockEntity;
import baguchi.crafters_kitchen.registry.ModBlockEntitys;
import baguchi.crafters_kitchen.registry.ModBlocks;
import baguchi.crafters_kitchen.utils.FoodUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SandwichBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);


    public SandwichBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof SandwichBlockEntity sandwichBlockEntity) {
            ItemStack heldStack = player.getItemInHand(handIn);

            if (player.isShiftKeyDown()) {
                if (sandwichBlockEntity.addFoodDecoItem(heldStack.copy(), hit.getLocation().subtract(pos.getCenter()))) {
                    worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldStack.getFoodProperties(player) != null && sandwichBlockEntity.addFoodItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack, hit.getLocation().subtract(pos.getCenter()))) {
                worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                return InteractionResult.SUCCESS;
            }

        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof SandwichBlockEntity sandwichBlockEntity) {
            sandwichBlockEntity.foodHolder = FoodUtils.readFoodDataFromItemStack(stack.copy());
        }
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof SandwichBlockEntity sandwichBlockEntity) {
                ItemStack stack = new ItemStack(ModBlocks.SANDWICH.get());
                FoodUtils.writeFoodData(stack.getOrCreateTag(), sandwichBlockEntity.getFoodHolder());
                Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof SandwichBlockEntity) {
            return ((SandwichBlockEntity) tileEntity).foodHolder.stack().size() <= 15 ? ((SandwichBlockEntity) tileEntity).foodHolder.stack().size() : 0;
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntitys.SANDWICH.get().create(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

}
