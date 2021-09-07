package me.gavin.blm.misc;

import me.gavin.blm.BLMClient;
import net.minecraft.client.Minecraft;

public interface MC {

    Minecraft mc = Minecraft.getMinecraft();

    BLMClient blm = BLMClient.INSTANCE;
}
