package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;

public abstract class AbstractBranchBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction> ATTACHMENT = DistantMoonsBlockStateProperties.BRANCH_ATTACHMENT;
  public static final BooleanProperty EXTENDED = DistantMoonsBlockStateProperties.EXTENDED;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public AbstractBranchBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(ATTACHMENT, Direction.DOWN)
        .setValue(EXTENDED, false)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(ATTACHMENT, EXTENDED, WATERLOGGED);
  }

  @Override
  protected @NonNull FluidState getFluidState(@NonNull BlockState blockState) {
    return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, @NonNull BlockGetter level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @NonNull Fluid fluid) {
    return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, level, blockPos, blockState, fluid);
  }

  @Override
  public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @NonNull FluidState fluidState) {
    return SimpleWaterloggedBlock.super.placeLiquid(level, blockPos, blockState, fluidState);
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState blockState, @NonNull PathComputationType type) {
    if (type == PathComputationType.WATER) return blockState.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean propagatesSkylightDown(BlockState blockState) {
    return !blockState.getValue(WATERLOGGED);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    Direction face = context.getClickedFace();
    return this.updateState(
        level, blockPos,
        this.defaultBlockState()
            .setValue(ATTACHMENT, face == Direction.DOWN ? face : face.getOpposite())
            .setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER)
    );
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState blockState,
      @NonNull LevelReader level,
      ScheduledTickAccess tickView,
      @NonNull BlockPos blockPos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    tickView.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    BlockState updatedState = this.updateState(level, blockPos, blockState);
    return updatedState != null ? updatedState : Blocks.AIR.defaultBlockState();
  }

  protected @Nullable BlockState updateState(BlockGetter level, BlockPos blockPos, BlockState blockState) {
    Direction direction = blockState.getValue(ATTACHMENT);
    BlockPos attachedPos = blockPos.relative(direction);
    BlockState attachedState = level.getBlockState(attachedPos);
    if (attachedState.is(neverConnectsTo())) return null;
    if (attachedState.is(alwaysConnectsTo())) return blockState.setValue(EXTENDED, extendsTo(attachedState, direction));
    if (!attachedState.isFaceSturdy(level, attachedPos, direction.getOpposite())) return null;
    return blockState.setValue(EXTENDED, extendsTo(attachedState, direction));
  }

  private boolean extendsTo(BlockState state, Direction direction) {
    Direction.Axis axis = direction.getAxis();
    if (state.is(axis == Direction.Axis.Y ? extendsToVertically() : extendsToHorizontally())) return true;
    if (state.getBlock() instanceof AbstractBranchBlock) return direction.getAxis() != Direction.Axis.Y && state.getValue(ATTACHMENT) != direction.getOpposite();
    if (state.getBlock() instanceof AbstractPoleBlock) return state.getValue(AbstractPoleBlock.AXIS) != axis;
    return false;
  }

  protected abstract TagKey<Block> alwaysConnectsTo();
  protected abstract TagKey<Block> extendsToHorizontally();
  protected abstract TagKey<Block> extendsToVertically();
  protected abstract TagKey<Block> neverConnectsTo();

  @Override
  protected @NonNull VoxelShape getShape(@NonNull BlockState blockState, @NonNull BlockGetter level, @NonNull BlockPos blockPos, @NonNull CollisionContext context) {
    Direction direction = blockState.getValue(ATTACHMENT);
    VoxelShape centerShape = getCenterShapeByDirection(direction);
    return blockState.getValue(EXTENDED) ? Shapes.or(centerShape, getExtensionShapeByDirection(direction)) : centerShape;
  }

  protected abstract VoxelShape getCenterShapeByDirection(Direction direction);
  protected abstract VoxelShape getExtensionShapeByDirection(Direction direction);

  @Override
  protected @NonNull BlockState rotate(@NonNull BlockState blockState, @NonNull Rotation rotation) {
    return blockState.setValue(ATTACHMENT, rotation.rotate(blockState.getValue(ATTACHMENT)));
  }

  @Override
  protected @NonNull BlockState mirror(@NonNull BlockState blockState, @NonNull Mirror mirror) {
    return blockState.setValue(ATTACHMENT, mirror.mirror(blockState.getValue(ATTACHMENT)));
  }
}
