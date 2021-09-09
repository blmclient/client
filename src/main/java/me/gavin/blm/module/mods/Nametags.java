package me.gavin.blm.module.mods;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.blm.events.RenderPlayerNameplateEvent;
import me.gavin.blm.misc.ProjectionUtils;
import me.gavin.blm.misc.RenderUtil;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;
import me.gavin.blm.setting.BoolSetting;
import me.gavin.blm.setting.NumberSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "Nametags",
        description = "Better nametags",
        category = Module.Category.Render
)
public final class Nametags extends Module {

    private NumberSetting scale = new NumberSetting("Scale", 2.5f, 1.0f, 5.0f);
    private BoolSetting health = new BoolSetting("Health", true);
    private BoolSetting ping = new BoolSetting("Ping", true);
    private BoolSetting armor = new BoolSetting("Armor", true);
    private BoolSetting items = new BoolSetting("Items", true);

    @Override
    protected void init() {
        addSettings(scale, health, ping, armor, items);
    }

    @SubscribeEvent
    public void onRenderPlayerNameplate(RenderPlayerNameplateEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player.equals(mc.thePlayer))
                continue;

            final double yAdd =  player.isSneaking() ? 1.75 : 2.25;

            final double deltaX = Util.interpolate(player.posX, player.lastTickPosX, event.getPartialTicks());
            final double deltaY = Util.interpolate(player.posY, player.lastTickPosY, event.getPartialTicks());
            final double deltaZ = Util.interpolate(player.posZ, player.lastTickPosZ, event.getPartialTicks());

            final Vec3d projection = ProjectionUtils.toScaledScreenPos(new Vec3d(deltaX, deltaY + yAdd, deltaZ));

            GlStateManager.pushMatrix();
            GlStateManager.translate(projection.xCoord, projection.yCoord, 0);
            int ping = -1;
            if (mc.getConnection().getPlayerInfo(player.getName()) != null) {
                ping = mc.getConnection().getPlayerInfo(player.getName()).getResponseTime();
            }

            final double health = player.getHealth() + player.getAbsorptionAmount();

            String str = "";
            if (this.ping.getValue()) {
                str += ChatFormatting.GRAY + "" + ping + "ms " + ChatFormatting.RESET;
            }
            str += (player.isSneaking() ? ChatFormatting.GOLD : "") + player.getName();
            if (this.health.getValue()) {
                str += " " + getHealthColor(health) + String.format("%.1f", health);
            }
            Gui.drawRect(
                    -((mc.fontRendererObj.getStringWidth(str) + 2) / 2),
                    -(mc.fontRendererObj.FONT_HEIGHT + 2 ),
                    (mc.fontRendererObj.getStringWidth(str) + 2) / 2,
                    1,
                    0x90111111);

            mc.fontRendererObj.drawStringWithShadow(str, -(mc.fontRendererObj.getStringWidth(str) / 2f), -(mc.fontRendererObj.FONT_HEIGHT), -1);

            int y = -(mc.fontRendererObj.FONT_HEIGHT * 3);
            if (this.armor.getValue()) {
                int x = -30;

                for (ItemStack armorPiece : player.inventory.armorInventory) {
                    if (armorPiece == null) {
                        x += 15;
                        continue;
                    }

                    RenderUtil.renderItem(armorPiece, x, y);

                    x += 15;
                }
            }

            if (this.items.getValue()) {
                if (player.inventory.getStackInSlot(player.inventory.currentItem) != null) {
                    RenderUtil.renderItem(player.inventory.getStackInSlot(player.inventory.currentItem), -48, y);
                }

                // offhand
                if (player.getHeldItemOffhand() != null) {
                    RenderUtil.renderItem(player.getHeldItemOffhand(), 35, y);
                }
            }

            GlStateManager.popMatrix();
        }
    }

    private ChatFormatting getHealthColor(double health) {
        if (health >= 15.0) {
            return ChatFormatting.GREEN;
        } else if (health >= 10.0) {
            return ChatFormatting.YELLOW;
        } else if (health >= 5.0) {
            return ChatFormatting.RED;
        } else {
            return ChatFormatting.DARK_RED;
        }
    }
}