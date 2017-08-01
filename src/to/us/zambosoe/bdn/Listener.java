package to.us.zambosoe.bdn;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Logger;

public class Listener implements net.md_5.bungee.api.plugin.Listener {

    Main main;

    public Listener(Main main) {
        this.main = main;
    }

    //Connection
    @EventHandler
    public void Before_Connection(ServerConnectEvent e)
    {
        main.Load_Config();

        getLogger().info(e.getPlayer().getUniqueId().toString() + ": " + main.Get_Display_Name(e.getPlayer()));
    }
    private Logger getLogger() {
        return main.getLogger();
    }
}
