package to.us.zambosoe.bdn;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main extends Plugin
{
    //Global Variables
    public Configuration configuration = null;
    public Configuration playerConfig = null;
    public String pluginTag = null;

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
        getProxy().getPluginManager().registerListener(this, new to.us.zambosoe.bdn.Listener(this));

        //Get Config
        Load_Config();
    }

    public void Test_For_Config() {
        if (!getDataFolder().exists()){
            getDataFolder().mkdir();
            getLogger().info("Created new folder for the plugin.");
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
                getLogger().info("Created new config for the plugin.");
            } catch (IOException e) {
                getLogger().severe("Error: Could not create a config, do you have permission?");
            }
        }
    }

    //Load Config
    public void Load_Config(){
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            pluginTag = configuration.getString("Plugin_Tag"); //get tag
            pluginTag = ChatColor.translateAlternateColorCodes('&', pluginTag); //apply color

        } catch (IOException e) {
            getLogger().severe("Error: Could not load the config, creating a new one.");
            Test_For_Config();
        }
    }

    //Save Config
    public void Save_Config(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().severe("Error: Could not save the config, creating a new one.");
            Test_For_Config();
        }
    }

    //Config for players
    public void Create_Player_Config(){
        File file = new File(getDataFolder(), "players.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("players.yml")) {
                Files.copy(in, file.toPath());
                getLogger().info("Created new config for the players.");
            } catch (IOException e) {
                getLogger().severe("Error: Could not create a config for the players, do you have permission?");
            }
        }
    }

    public void Load_Player_Config(){
        try {
            playerConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "players.yml"));

        } catch (IOException e) {
            getLogger().severe("Error: Could not load the player config, creating a new one.");
            Create_Player_Config();
        }
    }

    public void Save_Player_Config(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(playerConfig, new File(getDataFolder(), "players.yml"));

        } catch (IOException e) {
            getLogger().severe("Error: Could not save the player config, creating a new one.");
            Create_Player_Config();
        }
    }

    public String Get_Display_Name(ProxiedPlayer player){
        String displayName = null;

        Load_Player_Config();
        if(playerConfig.get(player.getUniqueId().toString()) != null){
            displayName = playerConfig.getString(player.getUniqueId().toString());
        }else{
            playerConfig.set(player.getUniqueId().toString(), player.getName());
            Save_Config();

            displayName = playerConfig.getString(player.getUniqueId().toString());
        }

        player.setDisplayName(displayName);
        return displayName;
    }

    public String Set_Display_Name(ProxiedPlayer player, String newName){
        String displayName = null;

        Load_Player_Config();
        playerConfig.set(player.getUniqueId().toString(), newName);
        Save_Player_Config();

        if(configuration.getBoolean("Use_Colors")){
            displayName = ChatColor.translateAlternateColorCodes('&', playerConfig.getString(player.getUniqueId().toString()));
        }

        if(configuration.getBoolean("Use_Prefix")){
            String prefix = configuration.getString("Prefix");
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);

            displayName = prefix + displayName;
        }

        player.setDisplayName(displayName);
        return displayName;
    }
}
