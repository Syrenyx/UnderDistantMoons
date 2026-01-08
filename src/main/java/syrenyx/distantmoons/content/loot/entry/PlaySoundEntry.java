package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class PlaySoundEntry extends LootPoolSingletonContainer {

  public static final MapCodec<PlaySoundEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          SoundEvent.CODEC.fieldOf("sound").forGetter(entry -> entry.soundEvent),
          StringIdentifiableSoundCategory.CODEC.fieldOf("category").xmap(stringCategory -> stringCategory.category, StringIdentifiableSoundCategory::get).forGetter(entry -> entry.category),
          FloatProvider.codec(0.00001F, 10.0F).fieldOf("volume").forGetter(entry -> entry.volume),
          FloatProvider.codec(0.00001F, 2.0F).fieldOf("pitch").forGetter(entry -> entry.pitch),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("source", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.source),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, PlaySoundEntry::new)
  );
  protected final Holder<SoundEvent> soundEvent;
  protected final SoundSource category;
  protected final FloatProvider volume;
  protected final FloatProvider pitch;
  protected final OptionalEffectPoolEntryTarget source;

  protected PlaySoundEntry(
      Holder<SoundEvent> soundEvent,
      SoundSource category,
      FloatProvider volume,
      FloatProvider pitch,
      OptionalEffectPoolEntryTarget source,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.soundEvent = soundEvent;
    this.category = category;
    this.volume = volume;
    this.pitch = pitch;
    this.source = source;
  }

  @Override
  public @NonNull LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.PLAY_SOUND;
  }

  @Override
  public boolean expand(@NonNull LootContext context, @NonNull Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Vec3 pos = context.getOptionalParameter(LootContextParams.ORIGIN);
    if (pos == null) return true;
    RandomSource random = context.getRandom();
    context.getLevel().playSound(this.source.tryGettingEntityFromContext(context), pos.x(), pos.y(), pos.z(), this.soundEvent, this.category, this.volume.sample(random), this.pitch.sample(random));
    return true;
  }

  @Override
  protected void createItemStack(@NonNull Consumer<ItemStack> lootConsumer, @NonNull LootContext context) {}
}
