package syrenyx.distantmoons.references.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import syrenyx.distantmoons.utility.TagUtil;

public abstract class DistantMoonsBlockTags {

  public static final TagKey<Block> BARS = TagUtil.generateKey(RegistryKeys.BLOCK, "bars");
  public static final TagKey<Block> BARS_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/bars/always");
  public static final TagKey<Block> BARS_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/bars/never");
  public static final TagKey<Block> BEAM_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/beam/always");
  public static final TagKey<Block> BEAM_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/beam/never");
  public static final TagKey<Block> BRICK_FENCE = TagUtil.generateKey(RegistryKeys.BLOCK, "brick_fence");
  public static final TagKey<Block> BRICK_FENCE_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/brick_fence/always");
  public static final TagKey<Block> BRICK_FENCE_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/brick_fence/never");
  public static final TagKey<Block> CHAIN = TagUtil.generateKey(RegistryKeys.BLOCK, "chain");
  public static final TagKey<Block> CLIMBABLE = TagUtil.generateKey(RegistryKeys.BLOCK, "climbable");
  public static final TagKey<Block> COLORED_STAINED_GLASS = TagUtil.generateKey(RegistryKeys.BLOCK, "colored/stained_glass");
  public static final TagKey<Block> COLORED_STAINED_GLASS_PANE = TagUtil.generateKey(RegistryKeys.BLOCK, "colored/stained_glass_pane");
  public static final TagKey<Block> FIXED_LADDER = TagUtil.generateKey(RegistryKeys.BLOCK, "fixed_ladder");
  public static final TagKey<Block> FIXED_LADDER_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/fixed_ladder/always");
  public static final TagKey<Block> FIXED_LADDER_ATTACHES_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/fixed_ladder/attached");
  public static final TagKey<Block> FIXED_LADDER_CAPPED_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/fixed_ladder/capped");
  public static final TagKey<Block> FIXED_LADDER_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/fixed_ladder/never");
  public static final TagKey<Block> GLASS = TagUtil.generateKey(RegistryKeys.BLOCK, "glass");
  public static final TagKey<Block> GLASS_PANE = TagUtil.generateKey(RegistryKeys.BLOCK, "glass_pane");
  public static final TagKey<Block> GLASS_PANE_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/glass_pane/always");
  public static final TagKey<Block> GLASS_PANE_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/glass_pane/never");
  public static final TagKey<Block> IMMUNE_TO_DRAGON = TagUtil.generateKey(RegistryKeys.BLOCK, "immunity/ender_dragon");
  public static final TagKey<Block> IMMUNE_TO_WITHER = TagUtil.generateKey(RegistryKeys.BLOCK, "immunity/wither");
  public static final TagKey<Block> METAL_BAR_DOOR = TagUtil.generateKey(RegistryKeys.BLOCK, "metal_bar_door");
  public static final TagKey<Block> METAL_BAR_DOOR_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/metal_bar_door/never");
  public static final TagKey<Block> MINING_TIER_DIAMOND = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/diamond");
  public static final TagKey<Block> MINING_TIER_IRON = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/iron");
  public static final TagKey<Block> MINING_TIER_STONE = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/stone");
  public static final TagKey<Block> MINING_TYPE_AXE = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/axe");
  public static final TagKey<Block> MINING_TYPE_HOE = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/hoe");
  public static final TagKey<Block> MINING_TYPE_PICKAXE = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/pickaxe");
  public static final TagKey<Block> MINING_TYPE_SHOVEL = TagUtil.generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/shovel");
  public static final TagKey<Block> NEVER_CONNECT_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/never");
  public static final TagKey<Block> OXIDIZABLE_CUT_COPPER_WALL_SLAB = TagUtil.generateKey(RegistryKeys.BLOCK, "oxidizable/cut_copper_wall_slab");
  public static final TagKey<Block> OXIDIZABLE_IRON_BLOCK = TagUtil.generateKey(RegistryKeys.BLOCK, "oxidizable/iron_block");
  public static final TagKey<Block> POLE_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/pole/always");
  public static final TagKey<Block> POLE_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/pole/never");
  public static final TagKey<Block> SPIKED_FENCE = TagUtil.generateKey(RegistryKeys.BLOCK, "spiked_fence");
  public static final TagKey<Block> SPIKED_FENCE_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/spiked_fence/always");
  public static final TagKey<Block> SPIKED_FENCE_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/spiked_fence/never");
  public static final TagKey<Block> SPIKED_FENCE_NOT_BLOCKED_BY = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/spiked_fence/not_blocked_by");
  public static final TagKey<Block> WALL = TagUtil.generateKey(RegistryKeys.BLOCK, "wall");
  public static final TagKey<Block> WALL_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/wall/always");
  public static final TagKey<Block> WALL_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/wall/never");
  public static final TagKey<Block> WOODEN_BEAM = TagUtil.generateKey(RegistryKeys.BLOCK, "wooden_beam");
  public static final TagKey<Block> WOODEN_FENCE = TagUtil.generateKey(RegistryKeys.BLOCK, "wooden_fence");
  public static final TagKey<Block> WOODEN_FENCE_ALWAYS_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/wooden_fence/always");
  public static final TagKey<Block> WOODEN_FENCE_NEVER_CONNECTS_TO = TagUtil.generateKey(RegistryKeys.BLOCK, "connection_target/wooden_fence/never");
  public static final TagKey<Block> WOODEN_POLE = TagUtil.generateKey(RegistryKeys.BLOCK, "wooden_pole");
}
