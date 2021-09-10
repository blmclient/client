package me.gavin.blm.module.mods;

import me.gavin.blm.events.RenderEntityModelEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.ModeSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static org.lwjgl.opengl.GL11.*;

@Module.Info(
        name = "Chams",
        description = "Highlights entities",
        category = Module.Category.Render
)
public final class Chams extends Module {

    private final ModeSetting<ChamsMode> chamsMode = new ModeSetting<>("Mode", ChamsMode.Default);

    @SubscribeEvent
    public void onRenderEntityModel(RenderEntityModelEvent event) {
        event.setCanceled(true);

        if (chamsMode.getValue() == ChamsMode.XQZ) {
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glColor3f(1f, 0f, 0f);
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_LIGHTING);
            render(event);
            glEnable(GL_LIGHTING);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);
//        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glColor4f(1f, 1f, 1f, 1f);
            render(event);
        } else if (chamsMode.getValue() == ChamsMode.Default) {
            glDepthRange(0, 0.01);
            render(event);
            glDepthRange(0, 1.0);
        }
    }

    private void render(RenderEntityModelEvent event) {
        event.getModelBase().render(event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
    }

    public enum ChamsMode {
        Default,
        XQZ
    }
}
