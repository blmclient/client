package me.gavin.blm.module.mods;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.module.Module;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "PortalGodMode",
        description = "Take no damage after going thru a portal",
        category = Module.Category.Other
)
public class PortalGodMode extends Module {

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport) {}
    }
}
