package me.gavin.blm.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.gavin.blm.misc.MC;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public abstract class Module implements MC {
	
	private boolean enabled;
	private int bind;
	private final String name;
	private final String description;
	private final Category category;
	
	public Module() {
		if (this.getClass().isAnnotationPresent(Info.class)) {
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

	public enum Category {
		Combat,
		Movement,
		Render,
		World,
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
