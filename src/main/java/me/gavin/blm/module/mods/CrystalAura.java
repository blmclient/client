package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


// chinese crystal aura idc

// fix ur server scott
@Module.Info(
        name = "CrystalAura",
        description = "Break crystals automatically",
        category = Module.Category.Combat
)
public final class CrystalAura extends Module {

    private final NumberSetting range = new NumberSetting("Range", 4f, 1f, 5f);
    private final NumberSetting wallsRange = new NumberSetting("WallsRange", 3f, 1f, 5f);

    @SubscribeEvent
    public void onTick(PlayerUpdateEvent event) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal) {
                float range;
                if (mc.thePlayer.canEntityBeSeen(entity)) {
                    range = this.range.getValue();
                } else {
                    range = this.wallsRange.getValue();
                }

                // ensure we only break once per tick (because anticheat)
                if (mc.thePlayer.getDistanceToEntity(entity) <= range) {
                    mc.thePlayer.connection.sendPacket(new CPacketUseEntity(entity));
                    mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
                    return;
                }
            }
        }
    }
}
