package me.gavin.blm.gui.setting;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.blm.gui.api.Component;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;
import org.lwjgl.input.Keyboard;

public final class BindComponent extends Component {

    private final Module module;

    public BindComponent(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
    }

    private boolean binding = false;

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY) && mouseButton == 0) {
            binding = !binding;
            Util.uiButtonClick();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.outline2d(x, y, x + width, y + height, 0xff000000);
        final String text = binding ? "Press a key..." : ChatFormatting.GRAY + "Bind: " + ChatFormatting.WHITE + Keyboard.getKeyName(module.getBind());
        mc.fontRendererObj.drawStringWithShadow(text, x + 2f, 0.5f + y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f),-1);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (binding) {
            if (keyCode == (Keyboard.KEY_DELETE | Keyboard.KEY_BACK)) {
                module.setBind(0);
            } else {
                module.setBind(keyCode);
            }
            binding = false;
            Util.uiButtonClick();
        }
    }
}
