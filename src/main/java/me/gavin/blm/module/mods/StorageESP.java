package me.gavin.blm.module.mods;

import me.gavin.blm.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@Module.Info(
        name = "StorageESP",
        description = "ESP for storage blocks",
        category = Module.Category.Render
)
public final class StorageESP extends Module {

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityEnderChest) {
                GlStateManager.pushMatrix();
                GlStateManager.disableTexture2D();
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                final AxisAlignedBB bb = new AxisAlignedBB(tileEntity.getPos()).offset(
                        -mc.getRenderManager().viewerPosX,
                        -mc.getRenderManager().viewerPosY,
                        -mc.getRenderManager().viewerPosZ
                );
                final float[] color = color(tileEntity);
                RenderGlobal.renderFilledBox(bb, color[0], color[1], color[2], 0.4f);
                GlStateManager.enableDepth();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }
        }
    }

    private float[] color(TileEntity entity) {
        float[] rgb = new float[] {0f, 0f, 0f};

        if (entity instanceof TileEntityChest) {
            rgb[0] = 1f;
            rgb[1] = 0.5f;
        } else if (entity instanceof TileEntityFurnace) {
            rgb[0] = 0.5f;
            rgb[1] = 0.5f;
            rgb[2] = 0.5f;
        } else if (entity instanceof TileEntityEnderChest) {
            rgb[0] = 0.5f;
            rgb[2] = 1f;
        }

        return rgb;
    }
}
