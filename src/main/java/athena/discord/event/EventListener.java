package athena.discord.event;

import athena.discord.Main;
import athena.discord.webhook.DiscordWebhook;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.IOException;
import java.util.Objects;

public class EventListener implements Listener {
    private final Main plugin;

    public EventListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBanned(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if (p.isBanned()){
            String webhookUrl = plugin.data.getConfig("config.yml").getString("server.webhook-link");
            DiscordWebhook webhook = new DiscordWebhook(webhookUrl);

            String message = "\\r **Player Name**: " +p.getName() + "\\r" +
                    "**Reason** : " + Objects.requireNonNull(Bukkit.getBanList(BanList.Type.NAME).getBanEntry(p.getName())).getReason() + "\\r" +
                    "**Banned Date** : " + Objects.requireNonNull(Bukkit.getBanList(BanList.Type.NAME).getBanEntry(p.getName())).getCreated() + "\\r" +
                    "**Expiration** : " + Objects.requireNonNull(Bukkit.getBanList(BanList.Type.NAME).getBanEntry(p.getName())).getExpiration() + "\\r" +
                    "**Source** : " + Objects.requireNonNull(Bukkit.getBanList(BanList.Type.NAME).getBanEntry(p.getName())).getSource() + "\\r";

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(plugin.data.getConfig("config.yml").getString("format.title"))
                    .setFooter(plugin.data.getConfig("config.yml").getString("format.footer")
                            , plugin.data.getConfig("config.yml").getString("format.footer-images"))
                    .setDescription(message));
            try {
                webhook.execute();
            } catch (IOException error) {
                System.out.println("Failed to Execute Webhook");
                error.printStackTrace();

            }
        }
    }
}
