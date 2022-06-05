package athena.discord;

import athena.discord.data.DataManager;
import athena.discord.event.EventListener;
import athena.discord.webhook.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public DataManager data;
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic

        data = new DataManager(this);
        config.options().copyDefaults(true);
        saveConfig();

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        String webhookUrl = data.getConfig("config.yml").getString("server.webhook-link");
        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
        webhook.setAvatarUrl(data.getConfig("config.yml").getString("format.avatar-url"));
        webhook.setUsername(data.getConfig("config.yml").getString("format.username"));
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public FileConfiguration getConfigFile(){
        return config;
    }

}
