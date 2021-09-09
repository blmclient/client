package me.gavin.blm.gui.setting;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.blm.gui.api.Component;
import me.gavin.blm.misc.Util;
import me.gavin.blm.setting.ModeSetting;

public final class ModeComponent extends Component {

    private final ModeSetting<?> setting;

    public ModeComponent(ModeSetting<?> setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY)) {
            switch(mouseButton) {
                case 0:
                    setting.increment();
                    Util.uiButtonClick();
                    break;
                case 1:
                    setting.decrement();
                    Util.uiButtonClick();
                    break;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(ChatFormatting.GRAY + setting.getName() + ": " + ChatFormatting.WHITE + setting.getValue().toString(), x + 2f, 0.5f + y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f),-1);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) { }
}
