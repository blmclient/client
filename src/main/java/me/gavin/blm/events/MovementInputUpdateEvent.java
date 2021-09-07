package me.gavin.blm.events;

import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class MovementInputUpdateEvent extends Event {

    private final MovementInput movementInput;

    public MovementInputUpdateEvent(MovementInput input) {
        this.movementInput = input;
    }

    public MovementInput getMovementInput() {
        return movementInput;
    }
}
