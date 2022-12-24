package eu.ifine.finegeyserchat;
//fabric
import eu.ifine.finegeyserchat.listener.FabricEventListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FineGeyserChat implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        System.out.println("FineGeyserChat loaded!");

       // ServerPlayConnectionEvents.JOIN.register(FabricEventListener::onPlayerJoin);
       // ServerPlayConnectionEvents.DISCONNECT.register(FabricEventListener::onPlayerLeave);
    }
}
