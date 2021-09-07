package me.gavin.blm.module.mods;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "AntiHunger",
        description = "Tries to prevent hunger loss",
        category = Module.Category.Player
)
public final class AntiHunger extends Module {

    public final BoolSetting onGround = new BoolSetting("OnGround", true);
    public final BoolSetting spoofSprint = new BoolSetting("SpoofSprint", true);

    @Override
    protected void init() {
        addSettings(
                onGround,
                spoofSprint
        );
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (onGround.getValue()) {
            if (event.getPacket() instanceof CPacketPlayer && !mc.thePlayer.isElytraFlying() && !mc.thePlayer.isRiding()) {
                final CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();
                cPacketPlayer.onGround = !(mc.thePlayer.fallDistance <= 0) || mc.playerController.getIsHittingBlock();
            }
        } else if (spoofSprint.getValue()) {
            if (event.getPacket() instanceof CPacketEntityAction) {
                final CPacketEntityAction actionPacket = (CPacketEntityAction) event.getPacket();
                if (actionPacket.getAction() == CPacketEntityAction.Action.START_SPRINTING || actionPacket.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)
                    event.setCanceled(true);
            }
        }
    }
}