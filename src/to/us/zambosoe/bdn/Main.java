package to.us.zambosoe.bdn;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main
        extends Plugin
        implements Listener
{
    public Configuration c;
    public File f;
    public String pluginTag;
    public List<String> servers = new ArrayList();
    public int limit;
    public boolean countColors;
    public boolean countSpaces;

    public void onEnable()
    {
        getLogger().info("  ______               _                          ");
        getLogger().info(" |___  /              | |                         ");
        getLogger().info("    / / __ _ _ __ ___ | |__   ___  ___  ___   ___ ");
        getLogger().info("   / / / _` | '_ ` _ \\| '_ \\ / _ \\/ __|/ _ \\ / _ \\");
        getLogger().info("  / /_| (_| | | | | | | |_) | (_) \\__ \\ (_) |  __/");
        getLogger().info(" /_____\\__,_|_| |_| |_|_.__/ \\___/|___/\\___/ \\___|");

        getProxy().getPluginManager().registerCommand(this, new Realname(this));
        getProxy().getPluginManager().registerCommand(this, new Nickname(this));
        getProxy().getPluginManager().registerListener(this, this);

        cc();
        lc();
        sc();
    }

    public void cc()
    {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        this.f = new File(getDataFolder(), "config.yml");
        if (!this.f.exists()) {
            try
            {
                this.f.createNewFile();
                InputStream is = getResourceAsStream("config.yml");Throwable localThrowable6 = null;
                try
                {
                    OutputStream os = new FileOutputStream(this.f);Throwable localThrowable7 = null;
                    try
                    {
                        ByteStreams.copy(is, os);
                    }
                    catch (Throwable localThrowable1)
                    {
                        localThrowable7 = localThrowable1;throw localThrowable1;
                    }
                    finally {}
                }
                catch (Throwable localThrowable4)
                {
                    localThrowable6 = localThrowable4;throw localThrowable4;
                }
                finally
                {
                    if (is != null) {
                        if (localThrowable6 != null) {
                            try
                            {
                                is.close();
                            }
                            catch (Throwable localThrowable5)
                            {
                                localThrowable6.addSuppressed(localThrowable5);
                            }
                        } else {
                            is.close();
                        }
                    }
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }

    public void lc()
    {
        this.servers.clear();
        try
        {
            this.c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.f);
        }
        catch (IOException e)
        {
            e.getCause();
        }
        String pt;
        if (this.c.get("Plugin.Plugin_Tag") != null)
        {
            pt = this.c.getString("Plugin.Plugin_Tag");
            this.pluginTag = ChatColor.translateAlternateColorCodes('&', pt);
        }
        else
        {
            this.c.set("Plugin.Plugin_Tag", "&f&l[&9BDN&f&l]&9 ");
            this.pluginTag = ChatColor.translateAlternateColorCodes('&', this.c.getString("Plugin.Plugin_Tag"));
        }
        if (this.c.get("Plugin.Allowed_Servers") != null)
        {
            for (String s : this.c.getStringList("Plugin.Allowed_Servers")) {
                this.servers.add(s.toLowerCase());
            }
        }
        else
        {
            this.servers.add("example");
            this.servers.add("server");
            this.servers.add("names");
            this.c.set("Plugin.Allowed_Servers", this.servers);
        }
        if (this.c.get("Plugin.Limit_Name_Length.Count_Spaces") != null)
        {
            this.countSpaces = this.c.getBoolean("Plugin.Limit_Name_Length.Count_Spaces");
        }
        else
        {
            this.c.set("Plugin.Limit_Name_Length.Count_Spaces", Boolean.valueOf(true));
            this.countSpaces = this.c.getBoolean("Plugin.Limit_Name_Length.Count_Spaces");
        }
        if (this.c.get("Plugin.Limit_Name_Length.Count_&") != null)
        {
            this.countColors = this.c.getBoolean("Plugin.Limit_Name_Length.Count_&");
        }
        else
        {
            this.c.set("Plugin.Limit_Name_Length.Count_&", Boolean.valueOf(false));
            this.countColors = this.c.getBoolean("Plugin.Limit_Name_Length.Count_&");
        }
        if (this.c.get("Plugin.Limit_Name_Length.Max") != null)
        {
            this.limit = this.c.getInt("Plugin.Limit_Name_Length.Max");
        }
        else
        {
            this.c.set("Plugin.Limit_Name_Length.Max", "25");
            this.limit = this.c.getInt("Plugin.Limit_Name_Length.Max");
        }
    }

    public void sc()
    {
        try
        {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.c, new File(getDataFolder(), "config.yml"));
        }
        catch (IOException e)
        {
            e.getCause();
        }
    }

    @EventHandler
    public void beforconnect(ServerConnectEvent e)
    {
        lc();
        if (this.servers.contains(e.getTarget().getName().toLowerCase())) {
            if (this.c.get(e.getPlayer().getUniqueId().toString()) != null)
            {
                String s = this.c.getString(e.getPlayer().getUniqueId().toString());
                String co = ChatColor.translateAlternateColorCodes('&', s);
                e.getPlayer().setDisplayName(co);
            }
            else
            {
                this.c.set(e.getPlayer().getUniqueId().toString(), e.getPlayer().getDisplayName());
            }
        }
        sc();
    }

    @EventHandler
    public void postloginevent(PostLoginEvent e) {}

    public void onDisable()
    {
        getLogger().info("Disabled.");
    }
}
