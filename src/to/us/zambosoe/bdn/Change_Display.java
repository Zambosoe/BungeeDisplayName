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
        super("nickname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        main.Load_Config();

        if(strings.length == 0){
            commandSender.sendMessage(main.pluginTag + "/Nickname <newName> or <playerName> [newName]");
        }else{
            ProxiedPlayer sp = null;
            for(ProxiedPlayer p : main.getProxy().getPlayers()){
                if(p.getDisplayName().toLowerCase().contains(strings[0].toLowerCase())){
                    sp = p;
                }
            }
            if(sp != null){
                if(strings.length > 1){
                    //Change player name.
                    String newName = "";
                    for(int i = 1; i < strings.length; i++)
                    {
                        newName += strings[i] + " ";
                    }
                    newName = newName.trim();
                    if(main.configuration.getBoolean("Length_Limit")){
                        if( newName.length() <= main.configuration.getInt("Length")){
                            main.Change_Display_Name(sp, newName);
                            commandSender.sendMessage(main.pluginTag + "You changed " + sp.getName() + "'s name to: " + sp.getDisplayName());
                            sp.sendMessage(main.pluginTag + "Your name was changed to: " + sp.getDisplayName());
                        }else{
                            commandSender.sendMessage("The new name is: " + newName.length());
                            commandSender.sendMessage("But it must be under or equal to: " + main.configuration.getInt("Length"));
                        }
                    }else{
                        main.Change_Display_Name(sp, newName);
                        commandSender.sendMessage(main.pluginTag + "You changed " + sp.getName() + "'s name to: " + sp.getDisplayName());
                        sp.sendMessage(main.pluginTag + "Your name was changed to: " + sp.getDisplayName());
                    }
                }else{
                    commandSender.sendMessage(main.pluginTag + "The new name cannot be blank.");
                }
            }else{
                //Change Your name.
                if(commandSender instanceof ProxiedPlayer){
                    ProxiedPlayer pp = (ProxiedPlayer) commandSender;
                    String newName = "";
                    for(int i = 0; i < strings.length; i++)
                    {
                        newName += strings[i] + " ";
                    }
                    newName = newName.trim();
                    if(main.configuration.getBoolean("Length_Limit")) {
                        if (newName.length() <= main.configuration.getInt("Length")) {
                            main.Change_Display_Name(pp, newName);
                            pp.sendMessage(main.pluginTag + "Changed your display name to: " + pp.getDisplayName());
                        }else{
                            commandSender.sendMessage("The new name is: " + newName.length());
                            commandSender.sendMessage("But it must be under or equal to: " + main.configuration.getInt("Length"));
                        }
                    }else{
                        main.Change_Display_Name(pp, newName);
                        pp.sendMessage(main.pluginTag + "Changed your display name to: " + pp.getDisplayName());
                    }
                }else{
                    commandSender.sendMessage(main.pluginTag + "Only a player can change their display name.");
                }
            }
        }
    }
}
