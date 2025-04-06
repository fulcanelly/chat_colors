package me.fulcanelly.plugins.color_chat;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColorChatPlugin extends JavaPlugin implements Listener {

    private ChatColor[] allMinecraftChatColors = {
            ChatColor.BLACK, // Black
            ChatColor.DARK_BLUE, // Dark Blue
            ChatColor.DARK_GREEN, // Dark Green
            ChatColor.DARK_AQUA, // Dark Aqua
            ChatColor.DARK_RED, // Dark Red
            ChatColor.DARK_PURPLE, // Dark Purple
            ChatColor.GOLD, // Gold
            ChatColor.GRAY, // Gray
            ChatColor.DARK_GRAY, // Dark Gray
            ChatColor.BLUE, // Blue
            ChatColor.GREEN, // Green
            ChatColor.AQUA, // Aqua
            ChatColor.RED, // Red
            ChatColor.LIGHT_PURPLE, // Light Purple
            ChatColor.YELLOW, // Yellow
            ChatColor.WHITE // White
    };

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ColorChat plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ColorChat plugin has been disabled!");
    }

    @EventHandler
    void onChatEvent(AsyncPlayerChatEvent e) {

        int playerNameHash = e.getPlayer().getName().hashCode();

        int colorIndex = Math.abs(playerNameHash % allMinecraftChatColors.length);
        ChatColor color = allMinecraftChatColors[colorIndex];

        e.setFormat(buildFormat(color));
    }

    String buildFormat(ChatColor color) {
        return new StringBuilder()
                .append(color)
                .append(ChatColor.BOLD)
                .append(" [ ")
                .append("%s")
                .append(" ] ")
                .append(ChatColor.RESET)
                .append(color)
                .append("%s")
                .toString();

    }
}
