package me.fulcanelly.plugins.color_chat;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.plugin.Plugin;
import lombok.RequiredArgsConstructor;

/**
 * Repository for managing player chat color salts in the database
 */
@RequiredArgsConstructor
public class PlayerSaltRepository {
    private final Plugin plugin;
    private final String dbPath;
    private Connection connection;

    /**
     * Initializes the database connection and creates tables if they don't exist
     */
    public void initialize() {
        try {
            // Create the database directory if it doesn't exist
            File dbFile = new File(plugin.getDataFolder(), dbPath);
            if (!dbFile.getParentFile().exists()) {
                dbFile.getParentFile().mkdirs();
            }

            // Connect to the database
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());

            // Create tables if they don't exist
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            plugin.getLogger().severe("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates the necessary tables in the database
     *
     * @throws SQLException If an error occurs while creating the tables
     */
    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Create the player_salts table
            statement.execute(
                "CREATE TABLE IF NOT EXISTS player_salts (" +
                "uuid TEXT PRIMARY KEY, " +
                "salt TEXT NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
        }
    }

    /**
     * Gets the connection to the database
     *
     * @return The database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to close database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the salt for a player
     *
     * @param uuid The player's UUID
     * @return The player's salt, or null if not found
     */
    public String getPlayerSalt(UUID uuid) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT salt FROM player_salts WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("salt");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to get player salt: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sets the salt for a player
     *
     * @param uuid The player's UUID
     * @param salt The salt to set
     */
    public void setPlayerSalt(UUID uuid, String salt) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO player_salts (uuid, salt) VALUES (?, ?)")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, salt);
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to set player salt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates a random salt for a player
     *
     * @param uuid The player's UUID
     * @return The generated salt
     */
    public String generateRandomSalt(UUID uuid) {
        String salt = UUID.randomUUID().toString();
        setPlayerSalt(uuid, salt);
        return salt;
    }
}
