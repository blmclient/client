package me.gavin.blm.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.blm.gui.api.Component;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;
import net.minecraft.client.gui.Gui;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

import java.util.ArrayList;

public final class Button extends Component {

    public final int xpadding, ypadding;
    public boolean open;
    private final Module module;
    private final ArrayList<Component> components;

    public Button(Module module, int x, int y, int width, int height, int xpadding, int ypadding) {
        super(x, y, width, height);
        this.module = module;
        this.components = new ArrayList<>();
        this.xpadding = xpadding;
        this.ypadding = ypadding;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0:
                    module.toggle();
                    Util.uiButtonClick();
                    break;
                case 1:
                    open = !open;
                    Util.uiButtonClick();
                    break;
            }
        }

        if (open) {
            for (Component component : components) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (open) {
            for (Component component : components) {
                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        if (module.isEnabled())
            Gui.drawRect(x, y, x + width, y + height, 0xC8ff0000);

        final ChatFormatting clr = (module.isEnabled() ? WHITE : GRAY);
        mc.fontRendererObj.drawStringWithShadow(clr + module.getName(), x + 2f, y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f) + 0.5f, -1);
        final String s = open ? "-" : "+";
        mc.fontRendererObj.drawStringWithShadow(clr + s, x + width - mc.fontRendererObj.getStringWidth(s) - 2f, y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f) + 0.5f, -1);
        if (open) {
            if (module.isEnabled()) {
                final int settingHeight = getSettingsHeight();
                // left
                Gui.drawRect(x, y + height, x + 1, y + height + settingHeight, 0xc8ff0000);
                // top
                Gui.drawRect(x + 1, y + height, x + width - 1, y + height + ypadding - 1, 0xc8ff0000);
                // bottom
                Gui.drawRect(x + 1, y + height + settingHeight - 1, x + width - 1, y + height + settingHeight, 0xc8ff0000);
                // right
                Gui.drawRect(x + width - 1, y + height, x + width, y + height + settingHeight, 0xc8ff0000);
            }
            int yoffset = y + height + ypadding;
            for (Component component : components) {
                component.x = x + xpadding;
                component.y = yoffset;
                yoffset += component.height + ypadding;
                component.draw(mouseX, mouseY, partialTicks);
            }
            RenderUtil.outline2d(x, y, x + width, y + height + getSettingsHeight(), 0xff000000);
        } else {
            RenderUtil.outline2d(x, y, x + width, y + height, 0xff000000);
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (open) {
            for (Component component : components) {
                component.keyTyped(keyChar, keyCode);
            }
        }
    }

    public Module getModule() {
        return module;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getSettingsHeight() {
        int height = ypadding;
        for (Component component : components) {
            height += component.height + ypadding;
        }

        return height + 1;
    }
}