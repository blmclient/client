package me.gavin.blm.config;

import me.gavin.blm.BLMClient;
import me.gavin.blm.misc.MC;

import java.io.File;

public final class ConfigManager implements MC {

    private final File saveDir;

    public ConfigManager() {
        this.saveDir = new File(mc.mcDataDir, BLMClient.MODID);
        if (!saveDir.exists())
            saveDir.mkdir();


    }
}
