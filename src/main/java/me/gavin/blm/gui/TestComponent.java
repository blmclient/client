package me.gavin.blm.gui;

import me.gavin.blm.gui.api.DragComponent;
import net.minecraft.client.gui.Gui;

public class TestComponent extends DragComponent {
    public TestComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInside(mouseX, mouseY) && mouseButton == 0)
            startDragging(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            stopDragging();
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        updateDragPos(mouseX, mouseY);
        Gui.drawRect(x, y, x +width, y + height, 0x90000000);
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) {

    }
}
