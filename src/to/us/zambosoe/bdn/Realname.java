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
        if ((commandSender instanceof ProxiedPlayer))
        {
            ProxiedPlayer player = (ProxiedPlayer)commandSender;
            if (this.main.servers.contains(player.getServer().getInfo().getName().toLowerCase())) {
                if (player.hasPermission("bdn.realname"))
                {
                    if (strings.length == 0)
                    {
                        player.sendMessage(this.main.pluginTag + ChatColor.RED + "/Whois <name>");
                    }
                    else
                    {
                        if (strings.length == 1)
                        {
                            ProxiedPlayer target = null;
                            for (ProxiedPlayer p : this.main.getProxy().getPlayers()) {
                                if ((p.getDisplayName().equalsIgnoreCase(strings[0])) || (p.getName().equalsIgnoreCase(strings[0]))) {
                                    target = p;
                                }
                            }
                            if (target != null)
                            {
                                player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Real name: " + ChatColor.RESET + target.getName());
                                player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Display name: " + ChatColor.RESET + target.getDisplayName());
                            }
                            else
                            {
                                player.sendMessage(this.main.pluginTag + ChatColor.RED + "Player: " + ChatColor.RESET + strings[0] + ChatColor.RED + " not found.");
                            }
                        }
                        if (strings.length > 1) {
                            player.sendMessage(this.main.pluginTag + ChatColor.RED + "To many things in command.");
                        }
                    }
                }
                else {
                    player.sendMessage(this.main.pluginTag + ChatColor.RED + "Sorry, you don't have permission for this.");
                }
            }
        }
    }
}
