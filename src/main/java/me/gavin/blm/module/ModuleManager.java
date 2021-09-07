package me.gavin.blm.module;

import com.google.common.reflect.ClassPath;
import net.minecraft.launchwrapper.Launch;

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
        if (clazz.getDeclaredConstructors().length == 0) {
            final Module module = clazz.newInstance();
            modules.add(module);
            moduleMap.put(clazz, module);
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) moduleMap.get(clazz);
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
