package me.gavin.blm.setting;

import net.minecraft.util.math.MathHelper;

public final class NumberSetting extends Setting {

    private float value;
    private final float defaultValue;
    private final float min, max;

    public NumberSetting(String name, float value, float min, float max) {
        super(name);
        this.value = this.defaultValue = value;
        this.min = min;
        this.max = max;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public float getValue() {
        return value;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setValueClamped(float value) {
        this.value = MathHelper.clamp_float(value, this.min, this.max);
    }
}