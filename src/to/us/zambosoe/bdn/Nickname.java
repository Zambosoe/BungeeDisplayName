package to.us.zambosoe.bdn;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("ALL")
public class Nickname extends Command
{
    Main main;

    public Nickname(Main main)
    {
        super("Nickname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();
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
                //Set others name
                commandSender.sendMessage("Testing..");
            }else{
                commandSender.sendMessage(main.pluginTag + "/Nickname <newName>/<playerName> [newName]");
            }
        }
    }
}
