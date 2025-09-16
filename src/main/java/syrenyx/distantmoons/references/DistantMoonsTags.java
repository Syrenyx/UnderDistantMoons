package syrenyx.distantmoons.references;

import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsTags {

  public static final TagKey<Block> CLIMBABLE = generateKey(RegistryKeys.BLOCK, "climbable");
  public static final TagKey<Block> IMMUNE_TO_DRAGON = generateKey(RegistryKeys.BLOCK, "immunity/ender_dragon");
  public static final TagKey<Block> IMMUNE_TO_WITHER = generateKey(RegistryKeys.BLOCK, "immunity/wither");
  public static final TagKey<Block> MINING_TIER_DIAMOND = generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/diamond");
  public static final TagKey<Block> MINING_TIER_IRON = generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/iron");
  public static final TagKey<Block> MINING_TIER_STONE = generateKey(RegistryKeys.BLOCK, "mining_tool_group/tier/stone");
  public static final TagKey<Block> MINING_TYPE_AXE = generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/axe");
  public static final TagKey<Block> MINING_TYPE_HOE = generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/hoe");
  public static final TagKey<Block> MINING_TYPE_PICKAXE = generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/pickaxe");
  public static final TagKey<Block> MINING_TYPE_SHOVEL = generateKey(RegistryKeys.BLOCK, "mining_tool_group/type/shovel");
  public static final TagKey<Block> SPIKED_FENCE = generateKey(RegistryKeys.BLOCK, "spiked_fence");
  public static final TagKey<Block> SPIKED_FENCE_ALWAYS_CONNECTS_TO = generateKey(RegistryKeys.BLOCK, "connection_target/spiked_fence/always");
  public static final TagKey<Block> SPIKED_FENCE_NEVER_CONNECTS_TO = generateKey(RegistryKeys.BLOCK, "connection_target/spiked_fence/never");

  private static <T> TagKey<T> generateKey(RegistryKey<Registry<T>> registryKey, String id) {
    return TagKey.of(registryKey, UnderDistantMoons.identifierOf(id));
  }

}
