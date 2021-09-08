package me.gavin.blm.command.commands;

import com.mojang.authlib.GameProfile;
import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.concurrent.CopyOnWriteArrayList;

public final class FakePlayerCmd extends Command {
    public FakePlayerCmd() {
        super("fakeplayer", "Spawns a fake player entity", "fakeplayer [spawn|delete] [name]");
    }

    private final CopyOnWriteArrayList<EntityOtherPlayerMP> fakePlayers = new CopyOnWriteArrayList<>();

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            Util.sendClientMessage(getArgSyntax());
            return;
        }

        if (args[0].equalsIgnoreCase("spawn")) {
            for (EntityOtherPlayerMP playerMP : fakePlayers) {
                if (playerMP.getName().equalsIgnoreCase(args[1])) {
                    Util.sendClientMessage("A fake player already exists with that name");
                    return;
                }
            }

            final EntityOtherPlayerMP player = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(mc.thePlayer.getUniqueID(), args[1]));
            player.copyLocationAndAnglesFrom(mc.thePlayer);
            player.rotationYawHead = mc.thePlayer.rotationYaw;
            fakePlayers.add(player);
            mc.theWorld.addEntityToWorld((int) -((Math.random() * 6969) + 1), player);
            Util.sendClientMessage("Added \"" + args[1] + "\" to the world");
        } else if (args[0].equals("delete")) {
            for (EntityOtherPlayerMP playerMP : fakePlayers) {
                if (playerMP.getName().equalsIgnoreCase(args[1])) {
                    mc.theWorld.removeEntity(playerMP);
                    fakePlayers.remove(playerMP);
                    Util.sendClientMessage("Deleted " + args[1]);
                    return;
                }
            }

            Util.sendClientMessage("Failed to delete fakeplayer \"" + args[1] + "\"");
        }
    }
}
