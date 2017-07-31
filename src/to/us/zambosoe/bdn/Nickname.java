package to.us.zambosoe.bdn;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Nickname
        extends Command
{
    Main main;

    public Nickname(Main main)
    {
        super("Nickname");
        this.main = main;
    }

    public void execute(CommandSender commandSender, String[] strings)
    {
        if ((commandSender instanceof ProxiedPlayer))
        {
            ProxiedPlayer player = (ProxiedPlayer)commandSender;
            if (this.main.servers.contains(player.getServer().getInfo().getName().toLowerCase())) {
                if (player.hasPermission("bdn.nickname"))
                {
                    if (strings.length == 0)
                    {
                        player.sendMessage(this.main.pluginTag + ChatColor.RED + "/Nickname <name>/<player> <name>");
                    }
                    else
                    {
                        ProxiedPlayer target = null;
                        for (ProxiedPlayer p : this.main.getProxy().getPlayers()) {
                            if ((strings[0].equalsIgnoreCase(p.getDisplayName())) || (strings[0].equalsIgnoreCase(p.getName()))) {
                                target = p;
                            }
                        }
                        if (target != null)
                        {
                            if (player.hasPermission("bdn.nick.others"))
                            {
                                if (strings.length == 1)
                                {
                                    player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Name cannot be blank.");
                                }
                                else
                                {
                                    String nn = "";
                                    for (int i = 1; i < strings.length; i++) {
                                        nn = nn + strings[i] + " ";
                                    }
                                    String nick = nn.trim();
                                    if (nick.equalsIgnoreCase("reset"))
                                    {
                                        target.setDisplayName(target.getName());
                                        this.main.lc();
                                        this.main.c.set(target.getUniqueId().toString(), target.getName());
                                        this.main.sc();
                                        player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Changed " + ChatColor.RESET + target.getName() + ChatColor.GREEN + "'s name back to: " + ChatColor.RESET + target.getName());
                                        target.sendMessage(this.main.pluginTag + player.getDisplayName() + ChatColor.GREEN + " changed your name back to: " + ChatColor.RESET + target.getName());
                                    }
                                    else
                                    {
                                        if (player.hasPermission("bdn.nick.others.color"))
                                        {
                                            nn = ChatColor.translateAlternateColorCodes('&', nick);
                                        }
                                        else
                                        {
                                            nn = nick;
                                            player.sendMessage(this.main.pluginTag + ChatColor.RED + "Sorry, you can't color others name.");
                                        }
                                        int minus = 0;
                                        if (!this.main.countColors)
                                        {
                                            char[] ch = nn.toCharArray();
                                            for (char c : ch) {
                                                if (c == '�') {
                                                    minus += 2;
                                                }
                                            }
                                        }
                                        if (!this.main.countSpaces)
                                        {
                                            char[] ch = nn.toCharArray();
                                            for (char c : ch) {
                                                if (c == ' ') {
                                                    minus += 1;
                                                }
                                            }
                                        }
                                        if (nn.length() - minus <= this.main.limit)
                                        {
                                            target.setDisplayName(nn);
                                            this.main.lc();
                                            this.main.c.set(target.getUniqueId().toString(), nn);
                                            this.main.sc();
                                            player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Changed " + ChatColor.RESET + target.getName() + ChatColor.GREEN + "'s name to: " + ChatColor.RESET + nn);
                                            target.sendMessage(this.main.pluginTag + ChatColor.RESET + player.getDisplayName() + ChatColor.GREEN + " changed your name to: " + ChatColor.RESET + nn);
                                        }
                                        else
                                        {
                                            player.sendMessage(this.main.pluginTag + ChatColor.RED + "Name over: " + this.main.limit + " characters");
                                        }
                                    }
                                }
                            }
                            else {
                                player.sendMessage(this.main.pluginTag + ChatColor.RED + "Sorry, you can't nick others.");
                            }
                        }
                        else
                        {
                            String nn = "";
                            for (int i = 0; i < strings.length; i++) {
                                nn = nn + strings[i] + " ";
                            }
                            String nick = nn.trim();
                            if (nick.equalsIgnoreCase("reset"))
                            {
                                player.setDisplayName(player.getName());
                                this.main.lc();
                                this.main.c.set(player.getUniqueId().toString(), player.getName());
                                this.main.sc();
                                player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Changed your name back to: " + ChatColor.RESET + player.getName());
                            }
                            else
                            {
                                if (nn.contains("&"))
                                {
                                    if (player.hasPermission("bdn.nick.color")) {
                                        nn = ChatColor.translateAlternateColorCodes('&', nick);
                                    } else {
                                        player.sendMessage(this.main.pluginTag + ChatColor.RED + "Sorry, you can't color your name.");
                                    }
                                }
                                else {
                                    nn = nick;
                                }
                                int minus = 0;
                                if (!this.main.countColors)
                                {
                                    char[] ch = nn.toCharArray();
                                    for (char c : ch) {
                                        if (c == '�') {
                                            minus += 2;
                                        }
                                    }
                                }
                                if (!this.main.countSpaces)
                                {
                                    char[] ch = nn.toCharArray();
                                    for (char c : ch) {
                                        if (c == ' ') {
                                            minus += 1;
                                        }
                                    }
                                }
                                if (nn.length() - minus <= this.main.limit)
                                {
                                    player.setDisplayName(nn);
                                    this.main.lc();
                                    this.main.c.set(player.getUniqueId().toString(), nn);
                                    this.main.sc();
                                    player.sendMessage(this.main.pluginTag + ChatColor.GREEN + "Changed your name to: " + ChatColor.RESET + nn);
                                }
                                else
                                {
                                    player.sendMessage(this.main.pluginTag + ChatColor.RED + "Name over: " + this.main.limit + " characters");
                                }
                            }
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
