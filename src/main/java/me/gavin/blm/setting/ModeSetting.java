package me.gavin.blm.setting;

public final class ModeSetting<T extends Enum<?>> extends Setting {

    private final T[] constants;
    private T value;

    @SuppressWarnings("unchecked")
    public ModeSetting(String name, T value) {
        super(name);
        this.value = value;
        this.constants = (T[]) value.getClass().getEnumConstants();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void increment() {
        if (value.ordinal() > value.getClass().getEnumConstants().length - 1) {
            value = constants[0];
        } else {
            value = constants[value.ordinal() + 1];
        }
    }

    public void decrement() {
        if (value.ordinal() == 0) {
            value = constants[constants.length - 1];
        } else {
            value = constants[value.ordinal() - 1];
        }
    }
}
