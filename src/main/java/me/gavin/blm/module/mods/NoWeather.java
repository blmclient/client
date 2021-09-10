package me.gavin.blm.module.mods;

import me.gavin.blm.events.WorldWeatherEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@Module.Info(
//        name = "NoWeather",
//        description = "Removes weather effects",
//        category = Module.Category.Other
//)
public final class NoWeather extends Module {

    private final NumberSetting weatherStrength = new NumberSetting("WeatherStrength", 0f, 0f, 1f);

    @SubscribeEvent
    public void onRain(WorldWeatherEvent.Rain event) {
        event.setCanceled(true);
        event.setStrength(weatherStrength.getValue());
    }
}
