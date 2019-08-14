import java.util.Map;
import java.util.Objects;

public class II_Note {

  public interface Singer {

    String[] sing(String song);
  }

  public interface Songwriter {

    String compose(int chartPosition);
  }

  public interface SingerSongwriter extends Singer, Songwriter {

    String[] strum();

    void actSensitive();
  }

  public class Test1 implements SingerSongwriter {

    @Override
    public String[] strum() {
      return new String[]{"a", "b"};
    }

    @Override
    public void actSensitive() {

    }

    @Override
    public String[] sing(String song) {
      return new String[0];
    }

    @Override
    public String compose(int chartPosition) {
      return null;
    }
  }

  // Skeletal implementation class
  public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {

    // Entries in a modifiable map must override this method
    @Override
    public V setValue(V value) {
      throw new UnsupportedOperationException();
    }

    // Implements the general contract of Map.Entry.equals
    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<?, ?> e = (Map.Entry) o;
      return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
    }

    // Implements the general contract of Map.Entry.hashCode
    @Override
    public int hashCode() {
      return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
      return getKey() + "=" + getValue();
    }
  }

  // Generic Singleton Factory
}
