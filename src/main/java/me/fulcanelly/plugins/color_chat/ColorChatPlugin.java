package me.fulcanelly.plugins.color_chat;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ColorChatPlugin extends JavaPlugin implements Listener {

    private String[] allMinecraftChatColors = {
        "&0", // Black
        "&1", // Dark Blue
        "&2", // Dark Green
        "&3", // Dark Aqua
        "&4", // Dark Red
        "&5", // Dark Purple
        "&6", // Gold
        "&7", // Gray
        "&8", // Dark Gray
        "&9", // Blue
        "&a", // Green
        "&b", // Aqua
        "&c", // Red
        "&d", // Light Purple
        "&e", // Yellow
        "&f"  // White
    };

    private String[] specialChatColors = {
        "&k", // Obfuscated
        "&l", // Bold
        "&m", // Strikethrough
        "&n", // Underline
        "&o", // Italic
        "&r"  // Reset
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
        String color = allMinecraftChatColors[colorIndex];

        e.setMessage(color + e.getMessage());
    }
}
