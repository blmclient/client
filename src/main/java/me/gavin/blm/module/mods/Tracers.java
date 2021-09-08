package me.gavin.blm.module.mods;

import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import java.awt.*;

@Module.Info(
        name = "Tracers",
        description = "Draws lines to entities",
        category = Module.Category.Render
)
public final class Tracers extends Module {

    private final BoolSetting players = new BoolSetting("Players", true);
    private final BoolSetting animals = new BoolSetting("Animals", false);
    private final BoolSetting mobs = new BoolSetting("Mobs", false);
    private final BoolSetting items = new BoolSetting("Items", false);

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity.equals(mc.thePlayer))
                continue;

            if (tracerCheck(entity))
                drawTracer(entity, event.getPartialTicks());
        }
    }

    private void drawTracer(Entity entity, float partialTicks) {
        final double deltaX = Util.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        final double deltaY = Util.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        final double deltaZ = Util.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;

        final Vec3d camPos = ActiveRenderInfo.getPosition();
        final Color color = Util.normalizedRedGreen(MathHelper.clamp_float(Util.normalize(mc.getRenderViewEntity().getDistanceToEntity(entity), 10.0f, 50.0f), 0.0f, 1.0f));

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GlStateManager.glLineWidth(1f);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
        GL11.glEnable(GL32.GL_DEPTH_CLAMP);
        RenderUtil.line3d(deltaX, deltaY, deltaZ, camPos.xCoord, camPos.yCoord, camPos.zCoord);
        RenderUtil.line3d(deltaX, deltaY, deltaZ, deltaX, deltaY + entity.height, deltaZ);
        GL11.glDisable(GL32.GL_DEPTH_CLAMP);
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private boolean tracerCheck(Entity entity) {
        if (players.getValue() && entity instanceof EntityPlayer) {
            return true;
        } else if (animals.getValue() && entity instanceof EntityAnimal) {
            return true;
        } else if (mobs.getValue() && entity instanceof EntityMob) {
            return true;
        } else return items.getValue() && entity instanceof EntityItem;
    }
}
