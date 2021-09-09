package me.gavin.blm.module;

import com.google.gson.JsonObject;
import me.gavin.blm.config.Configurable;
import me.gavin.blm.misc.MC;
import me.gavin.blm.setting.BoolSetting;
import me.gavin.blm.setting.ModeSetting;
import me.gavin.blm.setting.Setting;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

public abstract class Module implements MC, Configurable {
	
	private boolean enabled;
	private int bind;
	private final String name;
	private final String description;
	private final Category category;
	private final ArrayList<Setting> settings;

	public Module() {
		if (this.getClass().isAnnotationPresent(Info.class)) {
			this.settings = new ArrayList<>();
			final Info info = this.getClass().getAnnotation(Info.class);
			this.name = info.name();
			this.description = info.description();
			this.category = info.category();
			this.bind = info.keybind();
			init();
		} else {
			throw new IllegalStateException("Module is missing @Info annotation");
		}
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getConfigGroup() {
		return "modules";
	}

	@Override
	public void saveProperties(JsonObject jsonObject) {
		jsonObject.addProperty("module_enabled", enabled);
		jsonObject.addProperty("module_bind", bind);
		for (Setting setting : settings) {
			if (setting instanceof BoolSetting) {
				jsonObject.addProperty(setting.getName(), ((BoolSetting)setting).getValue());
			} else if (setting instanceof ModeSetting) {
				jsonObject.addProperty(setting.getName(), ((ModeSetting<?>)setting).getValue().ordinal());
			}
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void writeProperties(JsonObject jsonObject) {
		bind = jsonObject.get("module_bind").getAsInt();
		setEnabled(jsonObject.get("module_enabled").getAsBoolean());
		for (Setting setting : settings) {
			if (jsonObject.get(setting.getName()) != null) {
				if (setting instanceof BoolSetting) {
					((BoolSetting)setting).setValue(jsonObject.get(setting.getName()).getAsBoolean());
				} else if (setting instanceof ModeSetting) {
					final ModeSetting modeSetting = (ModeSetting) setting;
					final int index = jsonObject.get(setting.getName()).getAsInt();
					if (index <= modeSetting.getValue().getClass().getEnumConstants().length - 1 && index >= 0) {
						modeSetting.setValue(modeSetting.getValue().getClass().getEnumConstants()[index]);
					}
				}
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public void enable() {
		enabled = true;
		MinecraftForge.EVENT_BUS.register(this);
		onEnable();
	}
	
	public void disable() {
		enabled = false;
		MinecraftForge.EVENT_BUS.unregister(this);
		onDisable();
	}
	
	public void toggle() {
		if (enabled) {
			disable();
		} else {
			enable();
		}
	}
	
	public void setEnabled(boolean enabled) {
		if (enabled) {
			enable();
		} else {
			disable();
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	protected void init() { }

	protected void onEnable() { }

	protected void onDisable() { }

	public ArrayList<Setting> getSettings() {
		return settings;
	}

	public enum Category {
		Combat,
		Movement,
		Render,
		Player,
		Chat,
		Other
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Info {

		String name();

		String description();

		Category category();

		int keybind() default Keyboard.KEY_NONE;

		boolean enabled() default false;
	}
}
