package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import me.gavin.blm.events.*;
import me.gavin.blm.module.mods.Fullbright;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
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

    public static void onLivingUpdateHookRedirect(Minecraft mc, GuiScreen screen) {
        if (!MinecraftForge.EVENT_BUS.post(new PortalGuiClosedEvent(screen))) {
            mc.displayGuiScreen(screen);
        }
    }

    public static boolean channelRead0Hook(Packet<?> packet) {
        return MinecraftForge.EVENT_BUS.post(new PacketEvent.Receive(packet));
    }

    public static void drawChatHook(int left, int top, int right, int bottom, int color) {
        if (!MinecraftForge.EVENT_BUS.post(new GuiChatBackgroundEvent())) {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }

    public static boolean renderEntityNameHook(
            AbstractClientPlayer player,
            double x, double y, double z,
            String name, double distanceSq) {
        return MinecraftForge.EVENT_BUS.post(new RenderPlayerNameplateEvent(player, x, y, z, name, distanceSq));
    }

    public static void renderHook(
            ModelBase modelBase,
            Entity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            float scaleFactor) {
        if (!MinecraftForge.EVENT_BUS.post(new RenderEntityModelEvent(modelBase, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor))) {
            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }

    public static boolean isPushedByWaterHook(int entityId) {
        return MinecraftForge.EVENT_BUS.post(new PlayerPushedByWaterEvent(entityId));
    }

    public static boolean applyEntityCollisionHook(Entity entity) {
        return MinecraftForge.EVENT_BUS.post(new EntityPushedEvent(entity));
    }

    public static boolean isCollidableHook(Block block) {
        return MinecraftForge.EVENT_BUS.post(new BlockCollisionEvent(block));
    }

    public static void setupFogHook(float partialTicks) {
        MinecraftForge.EVENT_BUS.post(new SetupFogPostEvent(partialTicks));
    }
}
