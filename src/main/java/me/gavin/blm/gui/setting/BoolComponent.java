package me.gavin.blm.gui.setting;

import me.gavin.blm.gui.api.Component;
import me.gavin.blm.gui.api.Rect;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import me.gavin.blm.setting.BoolSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class BoolComponent extends Component {

    private final Rect rect = new Rect(0, 0, 10, 10);
    private final BoolSetting setting;

    public BoolComponent(BoolSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY) && mouseButton == 0) {
            this.setting.toggle();
            Util.uiButtonClick();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        rect.x = x + width - 12;
        rect.y = y + 1;
        //RenderUtil.outline2d(x, y, x + width, y + height, 0xff000000);
        if (setting.getValue()) {
            Gui.drawRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0xc8ff0000);
            drawCheckmark(rect);
        }
        RenderUtil.outline2d(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0xff000000);
        mc.fontRendererObj.drawStringWithShadow(setting.getName(), x + 2f, 0.5f + y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f),-1);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) { }

    private void drawCheckmark(Rect rect) {
        final Tessellator tessellator = Tessellator.getInstance();
        final VertexBuffer buffer = tessellator.getBuffer();

        GlStateManager.disableTexture2D();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.glLineWidth(1f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        buffer.pos(rect.x + 1.0, rect.y + rect.height - 3.0, 0.0).endVertex();
        buffer.pos(rect.x + (rect.width / 2.0) - 1.0, rect.y + rect.height - 1.0, 0.0).endVertex();
        buffer.pos(rect.x + rect.width - 2.0, rect.y + 2.0, 0.0).endVertex();
        tessellator.draw();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
    }
}
