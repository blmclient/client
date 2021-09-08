package me.gavin.blm;

import me.gavin.blm.command.CommandManager;
import me.gavin.blm.misc.EventProcessor;
import me.gavin.blm.module.ModuleManager;
import net.minecraft.block.BlockPortal;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.lang.reflect.Method;

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

    private ModuleManager moduleManager;

    private CommandManager commandManager;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}