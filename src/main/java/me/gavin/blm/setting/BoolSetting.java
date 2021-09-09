package me.gavin.blm.setting;

public final class BoolSetting extends Setting {

    private boolean value;
    private final boolean defaultValue;

    public BoolSetting(String name, boolean value) {
        super(name);
        this.value = this.defaultValue = value;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }
}