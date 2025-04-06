package me.fulcanelly.plugins.color_chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * Listener for chat events in the ColorChat plugin
 */
@RequiredArgsConstructor
public class ChatListener implements Listener {
    private final ColorChatPlugin plugin;

    private final ChatColor[] allMinecraftChatColors = {
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

    /**
     * Handles chat events
     *
     * @param event The chat event
     */
    @EventHandler
    void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Get player's salt from database, or generate a new one if not found
        String salt = plugin.getPlayerSaltRepository().getPlayerSalt(playerUUID);
        if (salt == null) {
            salt = event.getPlayer().getName();
        }

        // Use salt to determine color
        int saltHash = salt.hashCode();
        int colorIndex = Math.abs(saltHash % allMinecraftChatColors.length);
        ChatColor color = allMinecraftChatColors[colorIndex];

        event.setFormat(buildFormat(color));
    }

    /**
     * Builds the chat format with the given color
     *
     * @param color The color to use
     * @return The formatted chat string
     */
    private String buildFormat(ChatColor color) {
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
