package me.fulcanelly.plugins.color_chat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.RequiredArgsConstructor;

/**
 * Command executor for the /salt command
 */
@RequiredArgsConstructor
public class SaltCommand implements CommandExecutor {
    private final ColorChatPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has permission
        if (!player.hasPermission(plugin.getConfig().getString("commands.salt.permission", "colorchat.salt"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("commands.salt.no-permission", "&cYou don't have permission to use this command!")));
            return true;
        }

        // Generate a new salt for the player
        plugin.getPlayerSaltRepository().generateRandomSalt(player.getUniqueId());

        // Send a success message to the player
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfig().getString("commands.salt.success", "&aYour chat color has been randomized!")));

        return true;
    }
}
