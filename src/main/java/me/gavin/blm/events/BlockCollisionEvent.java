package me.gavin.blm.events;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class BlockCollisionEvent extends Event {

    private final Block block;

    public BlockCollisionEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
