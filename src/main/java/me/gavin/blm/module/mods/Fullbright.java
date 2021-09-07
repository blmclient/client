package me.gavin.blm.module.mods;

import me.gavin.blm.module.Module;

@Module.Info(
        name = "Fullbright",
        description = "Makes the world bright",
        category = Module.Category.Render
)
public final class Fullbright extends Module {

    // See MixinEntityRenderer.updateLightmapHook
}
