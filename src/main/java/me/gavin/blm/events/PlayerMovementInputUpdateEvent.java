package me.gavin.blm.events;

import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerMovementInputUpdateEvent extends Event {

    private final MovementInput movementInput;

    public PlayerMovementInputUpdateEvent(MovementInput input) {
        this.movementInput = input;
    }

    public MovementInput getMovementInput() {
        return movementInput;
    }
}
