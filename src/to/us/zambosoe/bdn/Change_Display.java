package to.us.zambosoe.bdn;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("ALL")
public class Change_Display extends Command
{
    Main main;

    public Change_Display(Main main)
    {
        super("ChangeDisplay");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();
        if(commandSender.hasPermission("bdn.ChangeDisplay")){
            if(strings.length == 0){
                commandSender.sendMessage(main.pluginTag + "/Nickname <newName>/<playerName> [newName]");
            }else{
                if(strings.length == 1){
                    if(commandSender instanceof ProxiedPlayer){
                        ProxiedPlayer player = (ProxiedPlayer) commandSender;
                        main.Change_Display_Name(player, strings[0]);
                        player.sendMessage(main.pluginTag + "You set your name to: " + player.getDisplayName());
                    }else{
                        commandSender.sendMessage( main.pluginTag + "Only a player can set there Nickname.");
                    }
                }else if(strings.length == 2){
                    if(commandSender.hasPermission("bdn.nickname")) {
                        commandSender.sendMessage("Testing..");
                    }
                }else{
                    commandSender.sendMessage(main.pluginTag + "/Nickname <newName>/<playerName> [newName]");
                }
            }
        }else{
            commandSender.sendMessage(main.pluginTag + "You don't have permission for this command.");
        }
    }
}
