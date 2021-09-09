package me.gavin.blm.gui.setting;

import me.gavin.blm.gui.api.Component;
import me.gavin.blm.gui.api.Rect;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.setting.BoolSetting;
import net.minecraft.client.gui.Gui;

public final class BoolComponent extends Component {

    private final Rect rect = new Rect(0, 0, 10, 10);
    private final BoolSetting setting;

    public BoolComponent(BoolSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        rect.x = x + width - 12;
        rect.y = y + 1;
        RenderUtil.outline2d(x, y, x + width, y + height, 0xff000000);
        if (setting.getValue())
            Gui.drawRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0xc8ff0000);
        RenderUtil.outline2d(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 0xff000000);
        mc.fontRendererObj.drawStringWithShadow(setting.getName(), x + 2f, 0.5f + y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f),-1);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) { }
}
