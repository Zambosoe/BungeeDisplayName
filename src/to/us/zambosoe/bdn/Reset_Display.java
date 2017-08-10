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
        super("ResetDisplay");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();
        if(commandSender.hasPermission("bdn.ResetDisplay")){
            if(strings.length == 0){
                commandSender.sendMessage(main.pluginTag + "/ResetDisplay [playerName]");
            }else{
                if(commandSender instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer) commandSender;
                    main.Change_Display_Name(p, p.getName());
                    commandSender.sendMessage(main.pluginTag + "Reset display name to: " + p.getDisplayName());
                }else{
                    commandSender.sendMessage(main.pluginTag + "Only a player can reset their display name.");
                }
            }
        }else{
            commandSender.sendMessage(main.pluginTag + "You don't have permission for this command.");
        }
    }
}
