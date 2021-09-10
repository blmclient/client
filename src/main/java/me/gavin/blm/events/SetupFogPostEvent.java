package me.gavin.blm.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public final class SetupFogPostEvent extends Event {

    private final float partialTicks;

    public SetupFogPostEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
