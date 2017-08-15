package to.us.zambosoe.bdn;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("ALL")
public class Reset_Display extends Command
{
    Main main;

    public Reset_Display(Main main)
    {
        super("resetname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();
        if(main.configuration.getBoolean("Use_Permissions")){
            if(commandSender.hasPermission("bdn.resetname")){
                resetName(commandSender, strings);
            }else{
                commandSender.sendMessage(main.pluginTag + "You don't have permission for this command.");
            }
        }else{
            resetName(commandSender, strings);
        }
    }

    public void resetName(CommandSender commandSender, String[] strings){
        ProxiedPlayer sp = null;
        for(ProxiedPlayer p : main.getProxy().getPlayers()){
            if(p.getDisplayName().toLowerCase().contains(strings[0].toLowerCase())){
                sp = p;
            }
        }
        if(sp != null){
            main.Change_Display_Name(sp, sp.getName());
            commandSender.sendMessage(main.pluginTag + "You reset " + sp.getName() + "'s name to: " + sp.getDisplayName());
            sp.sendMessage(main.pluginTag + "Your name was reset to: " + sp.getDisplayName());
        }else{
            //Change Your name.
            if(commandSender instanceof ProxiedPlayer){
                ProxiedPlayer pp = (ProxiedPlayer) commandSender;
                main.Change_Display_Name(pp, pp.getName());
                pp.sendMessage(main.pluginTag + "Reset your name to: " + pp.getDisplayName());
            }else{
                commandSender.sendMessage(main.pluginTag + "Only a player can change their display name.");
            }
        }
    }
}
