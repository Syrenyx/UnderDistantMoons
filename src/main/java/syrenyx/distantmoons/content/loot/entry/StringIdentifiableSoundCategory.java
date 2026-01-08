package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum StringIdentifiableSoundCategory implements StringRepresentable {
  MASTER("master", SoundSource.MASTER),
  MUSIC("music", SoundSource.MUSIC),
  RECORDS("record", SoundSource.RECORDS),
  WEATHER("weather", SoundSource.WEATHER),
  BLOCKS("block", SoundSource.BLOCKS),
  HOSTILE("hostile", SoundSource.HOSTILE),
  NEUTRAL("neutral", SoundSource.NEUTRAL),
  PLAYERS("player", SoundSource.PLAYERS),
  AMBIENT("ambient", SoundSource.AMBIENT),
  VOICE("voice", SoundSource.VOICE),
  UI("ui", SoundSource.UI);

  public static final Codec<StringIdentifiableSoundCategory> CODEC = StringRepresentable.fromEnum(StringIdentifiableSoundCategory::values);
  private final String id;
  public final SoundSource category;

  StringIdentifiableSoundCategory(final String id, SoundSource category) {
    this.id = id;
    this.category = category;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  public static StringIdentifiableSoundCategory get(SoundSource category) {
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
