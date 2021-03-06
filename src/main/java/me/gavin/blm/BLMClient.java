package me.gavin.blm;

import me.gavin.blm.command.CommandManager;
import me.gavin.blm.config.ConfigManager;
import me.gavin.blm.gui.ClickGuiDisplayScreen;
import me.gavin.blm.misc.EventProcessor;
import me.gavin.blm.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(
        name = BLMClient.NAME,
        modid = BLMClient.MODID,
        version = BLMClient.VERSION,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.10.2]"
)
public final class BLMClient
{
    public static final String MODID = "blm";
    public static final String VERSION = "1.0";
    public static final String NAME = "BLM Client";

    @Mod.Instance(MODID)
    public static BLMClient INSTANCE;

    private ConfigManager configManager;

    private ModuleManager moduleManager;

    private CommandManager commandManager;

    private ClickGuiDisplayScreen clickGui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        configManager = new ConfigManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        clickGui = new ClickGuiDisplayScreen();
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.saveAll()));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        configManager.loadAll();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ClickGuiDisplayScreen getClickGui() {
        return clickGui;
    }
}