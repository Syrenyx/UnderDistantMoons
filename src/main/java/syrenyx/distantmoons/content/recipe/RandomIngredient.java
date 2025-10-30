package syrenyx.distantmoons.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class RandomIngredient {

  public static final Codec<RandomIngredient> CODEC = RecordCodecBuilder.create();

  private List<Ingredient> ingredientPool;
}
