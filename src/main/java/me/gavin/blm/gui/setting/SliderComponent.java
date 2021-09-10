package me.gavin.blm.gui.setting;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.blm.gui.api.Component;
import me.gavin.blm.misc.Util;
import me.gavin.blm.setting.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class SliderComponent extends Component {

    private final NumberSetting setting;

    public SliderComponent(NumberSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    private boolean dragging;

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
            Util.uiButtonClick();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            dragging = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        updateSlider(mouseX);
        mc.fontRendererObj.drawStringWithShadow(ChatFormatting.WHITE + setting.getName() + ": " + ChatFormatting.GRAY + setting.getValue(), x + 2f, 0.5f + y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f),-1);

        // rendering line
        final Tessellator tessellator = Tessellator.getInstance();
        final VertexBuffer buffer = tessellator.getBuffer();
        GlStateManager.disableTexture2D();
        GlStateManager.color(0, 0, 0, 1);
        GL11.glLineWidth(3f);
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        buffer.pos(x, y + height, 0.0).endVertex();
        buffer.pos(x + sliderWidth, y + height, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color(1f, 0f, 0f, 1f);
        GL11.glLineWidth(1f);
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        buffer.pos(x, y + height, 0.0).endVertex();
        buffer.pos(x + sliderWidth, y + height, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    private double sliderWidth;

    private void updateSlider(int mouseX) {
        double diff = Math.min(width, Math.max(0, mouseX - this.x));
        double min = this.setting.getMin();
        double max = this.setting.getMax();
        this.sliderWidth = width * (this.setting.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0) {
                this.setting.setValueClamped(this.setting.getMin());
            } else {
                double newValue = Util.roundToPlace(diff / width * (max - min) + min, 2);
                this.setting.setValueClamped((float)newValue);
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) { }
}
