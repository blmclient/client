package me.gavin.blm.setting;

public abstract class Setting {

    private final String name;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
