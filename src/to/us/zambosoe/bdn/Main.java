package to.us.zambosoe.bdn;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main extends Plugin implements Listener
{
    //Global Variables
    Configuration configuration = null;
    public File f;
    public String pluginTag;
    public List<String> servers = new ArrayList();
    public int limit;
    public boolean countColors;
    public boolean countSpaces;

    //Start Plugin
    public void onEnable()
    {
        getLogger().info("");
        getLogger().info("  ___");
        getLogger().info(" | _ )_  _ _ _  __ _ ___ ___ ");
        getLogger().info(" | _ \\ || | ' \\/ _` / -_) -_)");
        getLogger().info(" |___/\\_,_|_||_\\__, \\___\\___|");
        getLogger().info("  ___  _       |___/");
        getLogger().info(" |   \\(_)____ __| |__ _ _  _ ");
        getLogger().info(" | |) | (_-< '_ \\ / _` | || |");
        getLogger().info(" |___/|_/__/ .__/_\\__,_|\\_, |");
        getLogger().info(" | \\| |__ _|_|__  ___   |__/ ");
        getLogger().info(" | .` / _` | '  \\/ -_)");
        getLogger().info(" |_|\\_\\__,_|_|_|_\\___|");
        getLogger().info("");
        getProxy().getPluginManager().registerCommand(this, new Realname(this));
        getProxy().getPluginManager().registerCommand(this, new Nickname(this));
        getProxy().getPluginManager().registerListener(this, this);

        //Get Config
        Load_Config();
    }

    void Test_For_Config() {
        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
            getLogger().info("Created new folder for the plugin.");
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            getLogger().info("First time use!");
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
                getLogger().info("Created new config for the plugin.");
            } catch (IOException e) {
                getLogger().info("Could not create a config, do you have permission?");
            }
        }
    }

    //Load Config
    void Load_Config(){
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().severe("Error: Could not load the config, did you remove it?");
        }
    }

    //Save Config
    void Save_Config(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().severe("Error: Could not save the config, did you remove it?");
        }
    }


    //Connection
    @EventHandler
    public void Before_Connection(ServerConnectEvent e)
    {
        if (this.servers.contains(e.getTarget().getName().toLowerCase())) {
            if (configuration.get(e.getPlayer().getUniqueId().toString()) != null)
            {
                String s = configuration.getString(e.getPlayer().getUniqueId().toString());
                String co = ChatColor.translateAlternateColorCodes('&', s);
                e.getPlayer().setDisplayName(co);
            }
            else
            {
                configuration.set(e.getPlayer().getUniqueId().toString(), e.getPlayer().getDisplayName());
            }
        }
    }
}
