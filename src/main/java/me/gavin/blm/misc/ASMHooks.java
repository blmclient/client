package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import me.gavin.blm.events.HurtCameraEffectEvent;
import me.gavin.blm.events.MovementInputUpdateEvent;
import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.module.mods.Fullbright;
import me.gavin.blm.module.mods.PortalGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import net.minecraft.util.MovementInput;
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

    public static void onLivingUpdateHook() {
        MinecraftForge.EVENT_BUS.post(new PlayerUpdateEvent());
    }

    public static void onLivingUpdateHook(MovementInput movementInput) {
        MinecraftForge.EVENT_BUS.post(new MovementInputUpdateEvent(movementInput));
    }

    public static boolean hurtCameraEffectHook(float partialTicks) {
        return MinecraftForge.EVENT_BUS.post(new HurtCameraEffectEvent(partialTicks));
    }

    public static void onLivingUpdateHook(Minecraft mc, GuiScreen screen) {
        if (!BLMClient.INSTANCE.getModuleManager().getModule(PortalGui.class).isEnabled()) {
            mc.displayGuiScreen(screen);
        }
    }
}