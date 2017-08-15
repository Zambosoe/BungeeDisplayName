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
        if(main.configuration.getBoolean("Whitelist_On")){
            if(main.configuration.getStringList("Whitelisted_Servers").contains(e.getServer().getInfo().getName())){
                main.Load_Player_Config(); //Reload the players
                main.Check_Display_Name(e.getPlayer());
            }
        }else{
            main.Load_Player_Config(); //Reload the players
            main.Check_Display_Name(e.getPlayer());
        }

        //Tell who and what
        main.getLogger().info("Player (" + e.getPlayer() + ") logged in with display name of: " + main.playerConfig.get(e.getPlayer().getUniqueId().toString()));
    }
}
