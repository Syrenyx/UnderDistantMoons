package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public record CoiledBlockComponent(
    Optional<Identifier> block,
    int amount,
    Direction direction
) {

  public static final int DEFAULT_AMOUNT = 1;
  public static final Direction DEFAULT_DIRECTION = Direction.DOWN;
  public static final Codec<CoiledBlockComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.optionalFieldOf("block").forGetter(CoiledBlockComponent::block),
          Codec.INT.fieldOf("amount").forGetter(CoiledBlockComponent::amount),
          Direction.CODEC.optionalFieldOf("direction", DEFAULT_DIRECTION).forGetter(CoiledBlockComponent::direction)
      )
      .apply(instance, CoiledBlockComponent::new)
  );

  public CoiledBlockComponent(int amount) {
    this(Optional.empty(), amount, DEFAULT_DIRECTION);
  }

  public static class Builder{
    private Optional<Identifier> block = Optional.empty();
    private int amount = DEFAULT_AMOUNT;
    private Direction direction = DEFAULT_DIRECTION;

    public Builder block(Block block) {
      this.block = Optional.of(Registries.BLOCK.getId(block));
      return this;
    }

    public Builder amount(int amount) {
      this.amount = amount;
      return this;
    }

    public Builder direction(Direction direction) {
      this.direction = direction;
      return this;
    }

    public CoiledBlockComponent build() {
      return new CoiledBlockComponent(block, amount, direction);
    }
  }
}
