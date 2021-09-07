package me.gavin.blm.misc;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.events.PlayerChatEvent;
import me.gavin.blm.mixins.access.ICPacketChatMessage;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class EventProcessor {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();

            final PlayerChatEvent chatEvent = new PlayerChatEvent(packet.getMessage());

            if (MinecraftForge.EVENT_BUS.post(chatEvent))
                event.setCanceled(true);

            ((ICPacketChatMessage)packet).setMessageAccessor(chatEvent.getMessage());
        }
    }
}
