package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.module.mods.Fullbright;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.objectweb.asm.Type;

public final class ASMHooks {

    public static final String internalName = Type.getInternalName(ASMHooks.class);

    public static boolean sendPacketHook(Packet<?> packet) {
        return MinecraftForge.EVENT_BUS.post(new PacketEvent.Send(packet));
    }

    public static boolean receivePacketHook(Packet<?> packet) {
        return MinecraftForge.EVENT_BUS.post(new PacketEvent.Receive(packet));
    }

    public static float updateLightmapHook(float original) {
        if (BLMClient.INSTANCE.getModuleManager().getModule(Fullbright.class).isEnabled()) {
            return 32f;
        } else {
            return original;
        }
    }
}
