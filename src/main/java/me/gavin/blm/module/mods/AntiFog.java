package me.gavin.blm.module.mods;

import me.gavin.blm.events.SetupFogPostEvent;
import me.gavin.blm.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "AntiFog",
        description = "Removes fog",
        category = Module.Category.Render
)
public final class AntiFog extends Module {

    @SubscribeEvent
    public void onPostFog(SetupFogPostEvent event) {
        GlStateManager.disableFog();
    }
}
