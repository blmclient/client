package me.gavin.blm.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public abstract class WorldWeatherEvent extends Event {

    private float strength;

    public WorldWeatherEvent() {
        this.strength = 0;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public static final class Rain extends WorldWeatherEvent {
    }

    public static final class Thunder extends WorldWeatherEvent {
    }
}
