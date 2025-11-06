package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.StringIdentifiable;

public enum StringIdentifiableSoundCategory implements StringIdentifiable {
  MASTER("master", SoundCategory.MASTER),
  MUSIC("music", SoundCategory.MUSIC),
  RECORDS("record", SoundCategory.RECORDS),
  WEATHER("weather", SoundCategory.WEATHER),
  BLOCKS("block", SoundCategory.BLOCKS),
  HOSTILE("hostile", SoundCategory.HOSTILE),
  NEUTRAL("neutral", SoundCategory.NEUTRAL),
  PLAYERS("player", SoundCategory.PLAYERS),
  AMBIENT("ambient", SoundCategory.AMBIENT),
  VOICE("voice", SoundCategory.VOICE),
  UI("ui", SoundCategory.UI);

  public static final Codec<StringIdentifiableSoundCategory> CODEC = StringIdentifiable.createCodec(StringIdentifiableSoundCategory::values);
  private final String id;
  public final SoundCategory category;

  StringIdentifiableSoundCategory(final String id, SoundCategory category) {
    this.id = id;
    this.category = category;
  }

  @Override
  public String asString() {
    return this.id;
  }

  public static StringIdentifiableSoundCategory get(SoundCategory category) {
    return switch (category) {
      case MASTER -> MASTER;
      case MUSIC -> MUSIC;
      case RECORDS -> RECORDS;
      case WEATHER -> WEATHER;
      case BLOCKS -> BLOCKS;
      case HOSTILE -> HOSTILE;
      case NEUTRAL -> NEUTRAL;
      case PLAYERS -> PLAYERS;
      case AMBIENT -> AMBIENT;
      case VOICE -> VOICE;
      case UI -> UI;
    };
  }
}
