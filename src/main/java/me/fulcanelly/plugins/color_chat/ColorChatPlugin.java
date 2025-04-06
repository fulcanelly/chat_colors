package me.fulcanelly.plugins.color_chat;

import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

@Getter
public class ColorChatPlugin extends JavaPlugin {

    private PlayerSaltRepository playerSaltRepository;
    private SaltCommand saltCommand;
    private ChatListener chatListener;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Initialize database manager
        String dbPath = getConfig().getString("database.path", "database.db");
        playerSaltRepository = new PlayerSaltRepository(this, dbPath);
        playerSaltRepository.initialize();

        // Register command
        saltCommand = new SaltCommand(this);
        getCommand("salt").setExecutor(saltCommand);

        // Register listeners
        chatListener = new ChatListener(this);
        getServer().getPluginManager().registerEvents(chatListener, this);

        getLogger().info("ColorChat plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Close database connection
        if (playerSaltRepository != null) {
            playerSaltRepository.close();
        }

        getLogger().info("ColorChat plugin has been disabled!");
    }
}
