package me.gavin.blm.events;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class RenderPlayerNameplateEvent extends Event {

    private final AbstractClientPlayer entityIn;
    private final double x, y, z;
    private final String text;
    private final double distanceSq;

    public RenderPlayerNameplateEvent(AbstractClientPlayer entityIn, double x, double y, double z, String text, double distanceSq) {
        this.entityIn = entityIn;
        this.x = x;
        this.y = y;
        this.z = z;
        this.text = text;
        this.distanceSq = distanceSq;
    }

    public AbstractClientPlayer getEntityIn() {
        return entityIn;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getText() {
        return text;
    }

    public double getDistanceSq() {
        return distanceSq;
    }
}
