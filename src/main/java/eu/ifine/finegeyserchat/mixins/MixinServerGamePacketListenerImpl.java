package eu.ifine.finegeyserchat.mixins;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.player.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl implements ServerPlayerConnection {
    @Shadow
    public ServerPlayer player;

    //以系统消息的形式发送玩家消息
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSend(Packet<?> packet, CallbackInfo info) {
        if (packet instanceof ClientboundPlayerChatPacket chat) {
            UUID SenderUUID = chat.message().signedHeader().sender();
            Player senderplayer = this.player.getServer().getPlayerList().getPlayer(SenderUUID);
            String Platform = null;
            String Version = null;
            if (FloodgateApi.getInstance().isFloodgatePlayer(senderplayer.getUUID())) {
                Platform = "§a§lBedrock";
                Version = FloodgateApi.getInstance().getPlayer(senderplayer.getUUID()).getVersion();
            } else {
                Platform = "§c§lJava";
                Version = ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(senderplayer.getUUID())).getName();
            }
            //获取玩家游戏版本

            player.sendSystemMessage(Component.literal("§6[" + Platform + " §b§l" +"§6] §r" + senderplayer.getName().getString() + " §6§l>>§r " + chat.message().serverContent().getString()), false);
            info.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V",
            at = @At("HEAD"), cancellable = true)
    private void onSend(Packet<?> packet, @Nullable PacketSendListener packetSendListener, CallbackInfo info) {
        if (packet instanceof ClientboundPlayerChatHeaderPacket) {
            info.cancel();
        } else if (packet instanceof ClientboundPlayerChatPacket chat && packetSendListener != null) {
            UUID SenderUUID = chat.message().signedHeader().sender();
            Player senderplayer =  this.player.getServer().getPlayerList().getPlayer(SenderUUID);
            String Platform = null;
            String Version = null;
            if (FloodgateApi.getInstance().isFloodgatePlayer(senderplayer.getUUID())) {
                Platform = "§a§lBedrock";
                Version = FloodgateApi.getInstance().getPlayer(senderplayer.getUUID()).getVersion();
            } else {
                Platform = "§c§lJava";
                Version = ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(senderplayer.getUUID())).getName();
            }
            player.sendSystemMessage(Component.literal("§6["+Platform + "-§b§l"+Version+"§6] §r"+ senderplayer.getName().getString() +" §6§l>>§r "+chat.message().serverContent().getString()),false);
            info.cancel();
        }
    }
}