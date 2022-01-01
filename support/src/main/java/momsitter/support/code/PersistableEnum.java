package momsitter.support.code;

public interface PersistableEnum<T> {
    T getPersistentValue();

    default boolean isEqualsPersistentValue(T value) {
        return this.getPersistentValue().equals(value);
    }
}
