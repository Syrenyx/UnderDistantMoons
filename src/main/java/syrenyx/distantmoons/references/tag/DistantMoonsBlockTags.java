package syrenyx.distantmoons.references.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsBlockTags {

  public static final TagKey<Block> BARS = keyOf("bars");
  public static final TagKey<Block> BARS_ALWAYS_CONNECTS_TO = keyOf("connection_target/bars/always");
  public static final TagKey<Block> BARS_NEVER_CONNECTS_TO = keyOf("connection_target/bars/never");
  public static final TagKey<Block> BEAM_ALWAYS_CONNECTS_TO = keyOf("connection_target/beam/always");
  public static final TagKey<Block> BEAM_NEVER_CONNECTS_TO = keyOf("connection_target/beam/never");
  public static final TagKey<Block> BRICK_FENCE = keyOf("brick_fence");
  public static final TagKey<Block> BRICK_FENCE_ALWAYS_CONNECTS_TO = keyOf("connection_target/brick_fence/always");
  public static final TagKey<Block> BRICK_FENCE_NEVER_CONNECTS_TO = keyOf("connection_target/brick_fence/never");
  public static final TagKey<Block> CHAIN = keyOf("chain");
  public static final TagKey<Block> CLIMBABLE = keyOf("climbable");
  public static final TagKey<Block> COLORED_STAINED_GLASS = keyOf("colored/stained_glass");
  public static final TagKey<Block> COLORED_STAINED_GLASS_PANE = keyOf("colored/stained_glass_pane");
  public static final TagKey<Block> FIXED_LADDER = keyOf("fixed_ladder");
  public static final TagKey<Block> FIXED_LADDER_ALWAYS_CONNECTS_TO = keyOf("connection_target/fixed_ladder/always");
  public static final TagKey<Block> FIXED_LADDER_ATTACHES_TO = keyOf("connection_target/fixed_ladder/attached");
  public static final TagKey<Block> FIXED_LADDER_CAPPED_TO = keyOf("connection_target/fixed_ladder/capped");
  public static final TagKey<Block> FIXED_LADDER_NEVER_CONNECTS_TO = keyOf("connection_target/fixed_ladder/never");
  public static final TagKey<Block> GLASS = keyOf("glass");
  public static final TagKey<Block> GLASS_PANE = keyOf("glass_pane");
  public static final TagKey<Block> GLASS_PANE_ALWAYS_CONNECTS_TO = keyOf("connection_target/glass_pane/always");
  public static final TagKey<Block> GLASS_PANE_NEVER_CONNECTS_TO = keyOf("connection_target/glass_pane/never");
  public static final TagKey<Block> IMMUNE_TO_DRAGON = keyOf("immunity/ender_dragon");
  public static final TagKey<Block> IMMUNE_TO_WITHER = keyOf("immunity/wither");
  public static final TagKey<Block> LEAVES_WITHOUT_LEAF_PARTICLES = keyOf("leaves_without_leaf_particles");
  public static final TagKey<Block> METAL_BAR_DOOR = keyOf("metal_bar_door");
  public static final TagKey<Block> METAL_BAR_DOOR_NEVER_CONNECTS_TO = keyOf("connection_target/metal_bar_door/never");
  public static final TagKey<Block> MINING_TIER_DIAMOND = keyOf("mining_tool_group/tier/diamond");
  public static final TagKey<Block> MINING_TIER_IRON = keyOf("mining_tool_group/tier/iron");
  public static final TagKey<Block> MINING_TIER_STONE = keyOf("mining_tool_group/tier/stone");
  public static final TagKey<Block> MINING_TYPE_AXE = keyOf("mining_tool_group/type/axe");
  public static final TagKey<Block> MINING_TYPE_HOE = keyOf("mining_tool_group/type/hoe");
  public static final TagKey<Block> MINING_TYPE_PICKAXE = keyOf("mining_tool_group/type/pickaxe");
  public static final TagKey<Block> MINING_TYPE_SHOVEL = keyOf("mining_tool_group/type/shovel");
  public static final TagKey<Block> NEVER_CONNECT_TO = keyOf("connection_target/never");
  public static final TagKey<Block> OXIDIZABLE_CUT_COPPER_WALL_SLAB = keyOf("oxidizable/cut_copper_wall_slab");
  public static final TagKey<Block> OXIDIZABLE_IRON_BLOCK = keyOf("oxidizable/iron_block");
  public static final TagKey<Block> POLE_ALWAYS_CONNECTS_TO = keyOf("connection_target/pole/always");
  public static final TagKey<Block> POLE_NEVER_CONNECTS_TO = keyOf("connection_target/pole/never");
  public static final TagKey<Block> SPIKED_FENCE = keyOf("spiked_fence");
  public static final TagKey<Block> SPIKED_FENCE_ALWAYS_CONNECTS_TO = keyOf("connection_target/spiked_fence/always");
  public static final TagKey<Block> SPIKED_FENCE_NEVER_CONNECTS_TO = keyOf("connection_target/spiked_fence/never");
  public static final TagKey<Block> SPIKED_FENCE_NOT_BLOCKED_BY = keyOf("connection_target/spiked_fence/not_blocked_by");
  public static final TagKey<Block> WALL = keyOf("wall");
  public static final TagKey<Block> WALL_ALWAYS_CONNECTS_TO = keyOf("connection_target/wall/always");
  public static final TagKey<Block> WALL_NEVER_CONNECTS_TO = keyOf("connection_target/wall/never");
  public static final TagKey<Block> WOODEN_BEAM = keyOf("wooden_beam");
  public static final TagKey<Block> WOODEN_FENCE = keyOf("wooden_fence");
  public static final TagKey<Block> WOODEN_FENCE_ALWAYS_CONNECTS_TO = keyOf("connection_target/wooden_fence/always");
  public static final TagKey<Block> WOODEN_FENCE_NEVER_CONNECTS_TO = keyOf("connection_target/wooden_fence/never");
  public static final TagKey<Block> WOODEN_POLE = keyOf("wooden_pole");

  private static TagKey<Block> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, RegistryKeys.BLOCK);
  }
}
