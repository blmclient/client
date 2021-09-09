package me.gavin.blm.module.mods;

import me.gavin.blm.events.PacketEvent;
import me.gavin.blm.events.PlayerUpdateEvent;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import me.gavin.blm.setting.NumberSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;

@Module.Info(
        name = "KillAura",
        description = "Attack entities automatically",
        category = Module.Category.Combat
)
public final class KillAura extends Module {

    private final NumberSetting range = new NumberSetting("Range", 4f, 1f, 6f);
    private final BoolSetting swing = new BoolSetting("Swing Arm", true);
    private final BoolSetting players = new BoolSetting("Players", true);
    private final BoolSetting animals = new BoolSetting("Animals", true);
    private final BoolSetting mobs = new BoolSetting("Mobs", true);
    private final BoolSetting other = new BoolSetting("Other", false);

    @Override
    protected void init() {
        addSettings(
                range,
                swing,
                players,
                animals,
                mobs,
                other
        );
    }

    private EntityLivingBase targetEntity = null;

    @SubscribeEvent
    public void onLivingUpdate(PlayerUpdateEvent event) {
        if (targetEntity == null) {
            final EntityLivingBase entityLivingBase = mc.theWorld.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityLivingBase)
                    .map(entity -> (EntityLivingBase) entity)
                    .sorted(Comparator.comparing(e -> e.getDistanceToEntity(mc.thePlayer)))
                    .filter(this::targetCheck)
                    .findFirst().orElse(null);

            if (entityLivingBase != null)
                targetEntity = entityLivingBase;
        } else {
            if (targetCheck(targetEntity)) {
                attack(targetEntity);
            } else {
                targetEntity = null;
            }
        }
    }

    private boolean targetCheck(EntityLivingBase entity) {
        if (entity.getDistanceToEntity(mc.thePlayer) <= range.getValue() && entity.getHealth() > 0 && entity.isEntityAlive() && entity != mc.thePlayer) {
            if (entity instanceof EntityPlayer && players.getValue()) {
                return true;
            } else if (entity instanceof EntityAnimal && animals.getValue()) {
                return true;
            } else if (entity instanceof EntityMob && mobs.getValue()) {
                return true;
            } else return other.getValue();
        } else {
            return false;
        }
    }

    private void attack(EntityLivingBase entity) {
        if (mc.thePlayer.getCooledAttackStrength(0f) >= 1) {
            mc.playerController.attackEntity(mc.thePlayer, entity);
            if (swing.getValue())
                mc.thePlayer.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send event) {
        if (targetEntity != null) {
            if (event.getPacket() instanceof CPacketPlayer) {
                final CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();
                final float[] rotations = Util.calculateLookAt(targetEntity.posX, targetEntity.posY + targetEntity.getEyeHeight(), targetEntity.posZ, mc.thePlayer);

                cPacketPlayer.yaw = rotations[0];
                cPacketPlayer.pitch = rotations[1];
            }
        }
    }
}
