package me.gavin.blm.gui;

import me.gavin.blm.gui.api.Component;
import me.gavin.blm.gui.setting.BindComponent;
import me.gavin.blm.gui.setting.BoolComponent;
import me.gavin.blm.gui.setting.ModeComponent;
import me.gavin.blm.gui.setting.SliderComponent;
import me.gavin.blm.misc.MC;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import me.gavin.blm.setting.ModeSetting;
import me.gavin.blm.setting.NumberSetting;
import me.gavin.blm.setting.Setting;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

public final class ClickGuiDisplayScreen extends GuiScreen implements MC {

    private final ArrayList<Component> components;

    public ClickGuiDisplayScreen() {
        this.components = new ArrayList<>();

        int xoffset = 10;
        for (Module.Category category : Module.Category.values()) {
            final Frame frame = new Frame(category.name(), xoffset, 4, 120, 16, 1, 1);
            for (Module module : blm.getModuleManager().getCategoryMods(category)) {
                final Button button = new Button(module, 0, 0, frame.width - 2, 12, 1, 1);
                final int compWidth = button.width - 2;
                final int compHeight = button.height;
                button.getComponents().add(new BindComponent(module, 0, 0, compWidth, compHeight));
                for (Setting setting : module.getSettings()) {
                    if (setting instanceof BoolSetting) {
                        button.getComponents().add(new BoolComponent((BoolSetting) setting, 0, 0, compWidth, compHeight));
                    } else if (setting instanceof ModeSetting) {
                        button.getComponents().add(new ModeComponent((ModeSetting<?>) setting, 0, 0, compWidth, compHeight));
                    } else if (setting instanceof NumberSetting) {
                        button.getComponents().add(new SliderComponent((NumberSetting) setting, 0, 0, compWidth, compHeight));
                    }
                }
                frame.getComponents().add(button);
            }
            components.add(frame);
            xoffset += 130;
        }
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
        for (Component component : components) {
            component.keyTyped(keyChar, keyCode);
        }
        super.keyTyped(keyChar, keyCode);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int scroll = Mouse.getEventDWheel();

        if (scroll > 0) {
            scroll = 1;
        } else if (scroll < 0) {
            scroll = -1;
        }

        for (Component component : components) {
            component.y += (scroll * 10);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }
}
