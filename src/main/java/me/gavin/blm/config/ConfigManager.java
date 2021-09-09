package me.gavin.blm.config;

import me.gavin.blm.BLMClient;
import me.gavin.blm.misc.MC;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ConfigManager implements MC {

    private final HashMap<String, List<Configurable>> configurables;
    private final List<String> groups;
    private final File saveDir;

    public ConfigManager() {
        this.groups = new ArrayList<>();
        this.configurables = new HashMap<>();
        this.saveDir = new File(mc.mcDataDir, BLMClient.MODID);
        if (!saveDir.exists())
            saveDir.mkdir();
    }

    public void registerConfigurable(Configurable configurable) {
        final String group = configurable.getConfigGroup();
        if (!groups.contains(group))
            groups.add(group);

        if (!configurables.containsKey(group)) {
            configurables.put(group, new ArrayList<>());
        }

        configurables.get(group).add(configurable);
    }

    public void saveAll() {

    }

    public void saveGroup(String group) {

    }

    public void loadAll() {

    }

    public void loadGroup(String group) {

    }

    public File getSaveDir() {
        return saveDir;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<Configurable> getConfigurables(String group) {
        return configurables.get(group);
    }
}