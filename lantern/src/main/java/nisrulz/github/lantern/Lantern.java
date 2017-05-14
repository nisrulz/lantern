package nisrulz.github.lantern;

public class Lantern {

  private Lantern() {

  }

  private static class LazyHolder {
    static final Lantern INSTANCE = new Lantern();
  }

  public static Lantern getInstance() {
    return LazyHolder.INSTANCE;
  }
}
