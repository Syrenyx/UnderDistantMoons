package syrenyx.distantmoons.content.command;

public enum PermissionLevel {
  ALL(0),
  MODERATOR(1),
  GAMEMASTER(2),
  ADMIN(3),
  OWNER(4);

  private final int level;

  PermissionLevel(int level) {
    this.level = level;
  }

  public int get() {
    return this.level;
  }
}
