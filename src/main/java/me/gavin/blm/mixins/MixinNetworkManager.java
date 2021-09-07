package me.gavin.blm.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.gavin.blm.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public final class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacketHook(Packet<?> packetIn, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketEvent.Send(packetIn)))
            ci.cancel();
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void channelRead0Hook(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketEvent.Receive(p_channelRead0_2_)))
            ci.cancel();
    }
}
