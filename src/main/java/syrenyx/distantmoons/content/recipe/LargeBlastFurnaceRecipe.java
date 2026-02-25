package syrenyx.distantmoons.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsRecipeBookCategories;
import syrenyx.distantmoons.initializers.DistantMoonsRecipeSerializers;
import syrenyx.distantmoons.initializers.DistantMoonsRecipeTypes;

import java.util.function.IntFunction;

public class LargeBlastFurnaceRecipe extends SingleItemRecipe {

  private final Category category;
  private final int blastingSteps;
  private final int minimumHeat;

  public LargeBlastFurnaceRecipe(String group, Category category, Ingredient input, ItemStack result, int blastingSteps, int minimumHeat) {
    super(group, input, result);
    this.category = category;
    this.blastingSteps = blastingSteps;
    this.minimumHeat = minimumHeat;
  }

  @Override
  public @NonNull RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
    return DistantMoonsRecipeSerializers.BLASTING_RECIPE;
  }

  @Override
  public @NonNull RecipeType<? extends SingleItemRecipe> getType() {
    return DistantMoonsRecipeTypes.BLASTING;
  }

  @Override
  public @NonNull RecipeBookCategory recipeBookCategory() {
    return switch (this.category) {
      case MISC -> DistantMoonsRecipeBookCategories.LARGE_BLAST_FURNACE_MISC;
    };
  }

  public Category category() {
    return this.category;
  }
  
  public int blastingSteps() {
    return this.blastingSteps;
  }

  public int minimumHeat() {
    return this.minimumHeat;
  }

  public enum Category implements StringRepresentable {
    MISC(0, "misc");

    private static final IntFunction<Category> BY_ID = ByIdMap.continuous(category -> category.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<Category> CODEC = StringRepresentable.fromEnum(Category::values);
    public static final StreamCodec<ByteBuf, Category> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, category -> category.id);
    private final int id;
    private final String name;
    
    Category(final int id, final String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public @NonNull String getSerializedName() {
      return this.name;
    }
  }
  
  public static class Serializer implements RecipeSerializer<LargeBlastFurnaceRecipe> {

    private static final MapCodec<LargeBlastFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
        .group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(LargeBlastFurnaceRecipe::group),
            Category.CODEC.optionalFieldOf("category", Category.MISC).forGetter(LargeBlastFurnaceRecipe::category),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(LargeBlastFurnaceRecipe::input),
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("result").forGetter(LargeBlastFurnaceRecipe::result),
            ExtraCodecs.POSITIVE_INT.fieldOf("blasting_steps").forGetter(LargeBlastFurnaceRecipe::blastingSteps),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("minimum_heat").forGetter(LargeBlastFurnaceRecipe::blastingSteps)
        )
        .apply(instance, LargeBlastFurnaceRecipe::new)
    );
    private static final StreamCodec<RegistryFriendlyByteBuf, LargeBlastFurnaceRecipe> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        LargeBlastFurnaceRecipe::group,
        Category.STREAM_CODEC,
        LargeBlastFurnaceRecipe::category,
        Ingredient.CONTENTS_STREAM_CODEC,
        LargeBlastFurnaceRecipe::input,
        ItemStack.STREAM_CODEC,
        LargeBlastFurnaceRecipe::result,
        ByteBufCodecs.INT,
        LargeBlastFurnaceRecipe::blastingSteps,
        ByteBufCodecs.INT,
        LargeBlastFurnaceRecipe::minimumHeat,
        LargeBlastFurnaceRecipe::new
    );

    @Override
    public @NonNull MapCodec<LargeBlastFurnaceRecipe> codec() {
      return CODEC;
    }

    @Override
    public @NonNull StreamCodec<RegistryFriendlyByteBuf, LargeBlastFurnaceRecipe> streamCodec() {
      return STREAM_CODEC;
    }
  }
}
