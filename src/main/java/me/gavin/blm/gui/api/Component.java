package me.gavin.blm.gui.api;

public abstract class Component extends Rect {


    public Component(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public abstract void draw(int mouseX, int mouseY, float partialTicks);

    public abstract void keyTyped(char keyChar, int keyCode);
}
