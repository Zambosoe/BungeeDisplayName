package to.us.zambosoe.bdn;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("ALL")
public class Listener implements net.md_5.bungee.api.plugin.Listener {

    Main main;

    public Listener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void On_Connect(ServerConnectedEvent e){
        main.Load_Config(); //Reload system config
        main.Load_Player_Config(); //Reload the players

        main.Check_Display_Name(e.getPlayer());
    }
}
