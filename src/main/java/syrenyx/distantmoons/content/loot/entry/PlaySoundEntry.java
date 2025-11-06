package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PlaySoundEntry extends LeafEntry {

  public static final MapCodec<PlaySoundEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          SoundEvent.ENTRY_CODEC.fieldOf("sound").forGetter(entry -> entry.soundEvent),
          StringIdentifiableSoundCategory.CODEC.fieldOf("category").xmap(stringCategory -> stringCategory.category, StringIdentifiableSoundCategory::get).forGetter(entry -> entry.category),
          FloatProvider.createValidatedCodec(0.00001F, 10.0F).fieldOf("volume").forGetter(entry -> entry.volume),
          FloatProvider.createValidatedCodec(0.00001F, 2.0F).fieldOf("pitch").forGetter(entry -> entry.pitch),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("source", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.source),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, PlaySoundEntry::new)
  );
  protected final RegistryEntry<SoundEvent> soundEvent;
  protected final SoundCategory category;
  protected final FloatProvider volume;
  protected final FloatProvider pitch;
  protected final OptionalEffectPoolEntryTarget source;

  protected PlaySoundEntry(
      RegistryEntry<SoundEvent> soundEvent,
      SoundCategory category,
      FloatProvider volume,
      FloatProvider pitch,
      OptionalEffectPoolEntryTarget source,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.soundEvent = soundEvent;
    this.category = category;
    this.volume = volume;
    this.pitch = pitch;
    this.source = source;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.PLAY_SOUND;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Vec3d pos = context.get(LootContextParameters.ORIGIN);
    if (pos == null) return true;
    Random random = context.getRandom();
    context.getWorld().playSound(this.source.tryGettingEntityFromContext(context), pos.getX(), pos.getY(), pos.getZ(), this.soundEvent, this.category, this.volume.get(random), this.pitch.get(random));
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
