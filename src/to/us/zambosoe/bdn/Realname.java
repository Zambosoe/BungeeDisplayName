package to.us.zambosoe.bdn;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Realname
        extends Command
{
    Main main;

    public Realname(Main main)
    {
        super("Realname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        //Disabled for testing.
    }
}
