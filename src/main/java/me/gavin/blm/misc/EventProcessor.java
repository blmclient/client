package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.events.PlayerChatEvent;
import me.gavin.blm.module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public final class EventProcessor implements MC {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();

            final PlayerChatEvent chatEvent = new PlayerChatEvent(packet.getMessage());

            if (MinecraftForge.EVENT_BUS.post(chatEvent))
                event.setCanceled(true);

            packet.message = chatEvent.getMessage();
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        final ScaledResolution sr = new ScaledResolution(mc);

        mc.fontRendererObj.drawStringWithShadow(DARK_RED + BLMClient.NAME + " " + BLMClient.VERSION, 2f, 2f, -1);
        int y = 2;
        for (Module module : blm.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                mc.fontRendererObj.drawStringWithShadow(DARK_RED + module.getName(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 2, y, -1);
                y += 1 + mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        ProjectionUtils.updateMatrix();
    }
}
