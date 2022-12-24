package eu.ifine.finegeyserchat.listener;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.player.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public class FabricEventListener {
    public static void onPlayerJoin(ServerGamePacketListenerImpl networkHandler, PacketSender packetSender, MinecraftServer server) {
        ServerPlayerConnection connection = networkHandler;
        Player player = connection.getPlayer();
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUUID())){
            player.sendSystemMessage(Component.literal("来自基岩版("+ FloodgateApi.getInstance().getPlayer(player.getUUID()).getVersion()+")的"+ player.getName()+"加入游戏"));
        }else{
            player.sendSystemMessage(Component.literal("来自Java版("+ ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(player.getUUID())).getName()+")的"+ player.getName()+"加入游戏"));
        }
    }
    public static void onPlayerLeave(ServerGamePacketListenerImpl networkHandler,MinecraftServer server) {
        ServerPlayerConnection connection = networkHandler;
        Player player = connection.getPlayer();
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUUID())){
            player.sendSystemMessage(Component.literal("来自基岩版("+ FloodgateApi.getInstance().getPlayer(player.getUUID()).getVersion()+")的"+ player.getName()+"退出游戏"));
        }else{
            player.sendSystemMessage(Component.literal("来自Java版("+ ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(player.getUUID())).getName()+")的"+ player.getName()+"退出游戏"));
        }
    }
}
