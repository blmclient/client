package me.gavin.blm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.gavin.blm.BLMClient;
import me.gavin.blm.misc.MC;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ConfigManager implements MC {

    private final HashMap<String, List<Configurable>> configurables;
    private final List<String> groups;
    private final File saveDir;
    private final Gson gson;
    private final JsonParser jsonParser;

    public ConfigManager() {
        this.jsonParser = new JsonParser();
        this.groups = new ArrayList<>();
        this.configurables = new HashMap<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
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
        for (String groupName : groups) {
            saveGroup(groupName);
        }
    }

    public void saveGroup(String group) {
        if (configurables.containsKey(group)) {
            for (Configurable configurable : configurables.get(group)) {
                final JsonObject object = new JsonObject();
                configurable.saveProperties(object);
                try {
                    saveJsonConfigurable(group, configurable.getName(), object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAll() {
        for (String groupName : groups) {
            loadGroup(groupName);
        }
    }

    public void loadGroup(String group) {
        if (configurables.containsKey(group)) {
            for (Configurable configurable : configurables.get(group)) {
                try {
                    final JsonObject object = loadJsonConfigurable(group, configurable.getName());
                    if (object != null) {
                        configurable.writeProperties(object);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveJsonConfigurable(String group, String name, JsonObject jsonObject) throws IOException {
        final File folder = new File(saveDir, group);
        if (!folder.exists())
            folder.mkdir();

        final File file = new File(folder, name + ".json");
        if (!file.exists())
            file.createNewFile();

        final FileWriter writer = new FileWriter(file);
        gson.toJson(jsonObject, writer);
        writer.flush();
        writer.close();
    }

    private JsonObject loadJsonConfigurable(String group, String name) throws IOException {
        final File groupFolder = new File(saveDir, group);
        if (groupFolder.exists()) {
            final File jsonFile = new File(groupFolder, name + ".json");
            if (jsonFile.exists()) {
                final FileReader reader = new FileReader(jsonFile);
                final JsonObject object = (JsonObject) jsonParser.parse(reader);
                reader.close();
                return object;
            }
        }

        return null;
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

    public Gson getGson() {
        return gson;
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }
}