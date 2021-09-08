package me.gavin.blm.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class EntityPushedByWaterEvent extends Event {

    private final int entityId;

    public EntityPushedByWaterEvent(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }
}
