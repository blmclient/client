package me.gavin.blm.module;

import com.google.common.reflect.ClassPath;
import me.gavin.blm.BLMClient;
import me.gavin.blm.gui.setting.BoolComponent;
import me.gavin.blm.gui.setting.ModeComponent;
import me.gavin.blm.setting.BoolSetting;
import me.gavin.blm.setting.ModeSetting;
import me.gavin.blm.setting.Setting;
import net.minecraft.launchwrapper.Launch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public final class ModuleManager {

    private final HashMap<Class<? extends Module>, Module> moduleMap = new HashMap<>();
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // load module classes
        try {
            for (ClassPath.ClassInfo info : ClassPath.from(Launch.classLoader).getAllClasses()) {
                if (info.getName().startsWith("me.gavin.blm.module.mods")) {
                    final Class<?> clazz = info.load();
                    if (!Modifier.isAbstract(clazz.getModifiers()) && Module.class.isAssignableFrom(clazz)) {
                        loadModule((Class<? extends Module>) clazz);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadModule(Class<? extends Module> clazz) throws Exception {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                final Module module = (Module) constructor.newInstance();
                try {
                    for (Field field : module.getClass().getDeclaredFields()) {
                        if (!field.isAccessible())
                            field.setAccessible(true);
                        if (Setting.class.isAssignableFrom(field.getType())) {
                            final Setting setting = (Setting) field.get(module);
                            module.getSettings().add(setting);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                modules.add(module);
                moduleMap.put(clazz, module);
                // register module config to be saved & loaded
                BLMClient.INSTANCE.getConfigManager().registerConfigurable(module);
            }
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) moduleMap.get(clazz);
    }

    @Deprecated
    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name))
                return module;
        }

        return null;
    }

    public ArrayList<Module> getCategoryMods(Module.Category category) {
        final ArrayList<Module> list = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category)
                list.add(module);
        }

        return list;
    }
}
