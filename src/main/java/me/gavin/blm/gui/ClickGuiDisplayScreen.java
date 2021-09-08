package me.gavin.blm.gui;

import me.gavin.blm.gui.api.Component;
import me.gavin.blm.misc.MC;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public final class ClickGuiDisplayScreen extends GuiScreen implements MC {

    private final ArrayList<Component> components;

    public ClickGuiDisplayScreen() {
        this.components = new ArrayList<>();

        components.add(new TestComponent(10, 10, 60, 60));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Component component : components) {
            component.draw(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);
    }

    public ArrayList<Component> getComponents() {
        return components;
    }
}
