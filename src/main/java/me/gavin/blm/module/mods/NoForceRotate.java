package me.gavin.blm.module.mods;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.module.Module;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "NoForceRotate",
        description = "Prevents forced server rotations",
        category = Module.Category.Player
)
public final class NoForceRotate extends Module {

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook posLookPacket = (SPacketPlayerPosLook) event.getPacket();
            posLookPacket.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            posLookPacket.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}