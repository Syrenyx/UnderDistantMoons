package syrenyx.distantmoons.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import syrenyx.distantmoons.initializers.DistantMoonsRecipeSerializers;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SeededShapedRecipe implements CraftingRecipe {
  final String group;
  final CraftingRecipeCategory category;
  final ItemStack result;
  final boolean showNotification;
  final RawShapedRecipe rawShapedRecipe;

  public SeededShapedRecipe(String group, CraftingRecipeCategory category, ItemStack result, boolean showNotification, RawShapedRecipe rawShapedRecipe) {
    this.group = group;
    this.category = category;
    this.result = result;
    this.showNotification = showNotification;
    this.rawShapedRecipe = rawShapedRecipe;
  }

  @Override
  public boolean matches(CraftingRecipeInput input, World world) {
    return false;
  }

  @Override
  public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
    return this.result.copy();
  }

  @Override
  public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
    return DistantMoonsRecipeSerializers.SEEDED_SHAPED;
  }

  @Override
  public IngredientPlacement getIngredientPlacement() {
    return null;
  }

  @Override
  public CraftingRecipeCategory getCategory() {
    return this.category;
  }

  @Override
  public boolean showNotification() {
    return this.showNotification;
  }

  public static class Serializer implements RecipeSerializer<SeededShapedRecipe> {

    public static final MapCodec<SeededShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(recipe -> recipe.showNotification),
                InputData.CODEC.xmap(recipe -> {
                  recipe.rawShapedRecipe = RawShapedRecipe.create();
                })
            )
            .apply(instance, SeededShapedRecipe::new)
    );
    public static final PacketCodec<RegistryByteBuf, SeededShapedRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        SeededShapedRecipe.Serializer::write, SeededShapedRecipe.Serializer::read
    );

    @Override
    public MapCodec<SeededShapedRecipe> codec() {
      return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, SeededShapedRecipe> packetCodec() {
      return PACKET_CODEC;
    }
  }

  public record InputData(Map<Character, RandomIngredient> key, List<String> pattern) {

    private static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap(pattern -> {
      if (pattern.size() > 3) return DataResult.error(() -> "Invalid pattern: too many rows, 3 is maximum");
      else if (pattern.isEmpty()) return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
      final int expectedLength = pattern.getFirst().length();
      for (String string : pattern) {
        if (string.length() > 3) return DataResult.error(() -> "Invalid pattern: too many columns, 3 is maximum");
        if (expectedLength != string.length()) return DataResult.error(() -> "Invalid pattern: each row must be the same width");
      }
      return DataResult.success(pattern);
    }, Function.identity());

    private static final Codec<Character> KEY_ENTRY_CODEC = Codec.STRING.comapFlatMap(keyEntry -> {
      if (keyEntry.length() != 1) return DataResult.error(() -> "Invalid key entry: '" + keyEntry + "' is an invalid symbol (must be 1 character only).");
      else return " ".equals(keyEntry) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(keyEntry.charAt(0));
    }, String::valueOf);

    public static final MapCodec<InputData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codecs.strictUnboundedMap(KEY_ENTRY_CODEC, RandomIngredient.CODEC).fieldOf("key").forGetter(data -> data.key),
        PATTERN_CODEC.fieldOf("pattern").forGetter(data -> data.pattern)
    ).apply(instance, InputData::new));
  }
}
