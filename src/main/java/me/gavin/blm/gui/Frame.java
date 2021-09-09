package me.gavin.blm.gui;

import me.gavin.blm.gui.api.Component;
import me.gavin.blm.gui.api.DragComponent;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public final class Frame extends DragComponent {

    private boolean open = true;
    private final int xpadding, ypadding;
    private final ArrayList<Button> components;
    private String title;

    public Frame(String title, int x, int y, int width, int height, int xpadding, int ypadding) {
        super(x, y, width, height);
        this.components = new ArrayList<>();
        this.title = title;
        this.xpadding = xpadding;
        this.ypadding = ypadding;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0:
                    startDragging(mouseX, mouseY);
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
        if (mouseButton == 0)
            stopDragging();

        if (open) {
            for (Component component : components) {
                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        updateDragPos(mouseX, mouseY);

        Gui.drawRect(x, y, x + width, y + height, 0xC8ff0000);
        RenderUtil.outline2d(x, y, x + width, y + height, 0xff000000);

        mc.fontRendererObj.drawStringWithShadow(title, x + 2f, y + (height / 2f) - (mc.fontRendererObj.FONT_HEIGHT / 2f), -1);

        if (open) {

            int yheight = y + height + ypadding;
            for (Button component : components) {
                yheight += (component.open ? component.getSettingsHeight() : 0) + component.height + component.ypadding;
            }

            Gui.drawRect(x, y + height, x + width, yheight, 0x90000000);
            RenderUtil.outline2d(x, y + height, x + width, yheight, 0xff000000);

            int yoffset = y + height + ypadding;
            for (Button component : components) {
                component.x = x + xpadding;
                component.y = yoffset;
                yoffset += (component.open ? component.getSettingsHeight() : 0) +component.height + ypadding;
                component.draw(mouseX, mouseY, partialTicks);
            }
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Button> getComponents() {
        return components;
    }
}