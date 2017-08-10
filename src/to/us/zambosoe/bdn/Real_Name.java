package to.us.zambosoe.bdn;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("ALL")
public class Real_Name extends Command
{
    Main main;

    public Real_Name(Main main)
    {
        super("Realname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();
        if(commandSender.hasPermission("bdn.realname")){
            if(strings.length == 0){
                commandSender.sendMessage(main.pluginTag + "Player name needed.");
            }else{
                if(strings.length == 1){
                    ProxiedPlayer sp = null;
                    for(ProxiedPlayer p : main.getProxy().getPlayers()){
                        if(p.getDisplayName().toLowerCase().contains(strings[0].toLowerCase())){
                            sp = p;
                        }
                    }
                    if(sp != null){
                        commandSender.sendMessage(main.pluginTag + sp.getName() + ": " + sp.getDisplayName());
                    }else{
                        commandSender.sendMessage(main.pluginTag + strings[0] + " could not be found.");
                    }
                }
            }
        }else{
            commandSender.sendMessage(main.pluginTag + "You don't have permission for this command.");
        }
    }
}
