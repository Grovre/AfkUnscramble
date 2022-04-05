package me.grovre.afkunscramble;

import me.grovre.afkunscramble.listeners.PlayerJoinListener;
import me.grovre.afkunscramble.listeners.PlayerLeaveListener;
import me.grovre.afkunscramble.listeners.UnscrambleAttemptListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AfkUnscramble extends JavaPlugin {

    public static AfkUnscramble plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Log("Plugin init");
        plugin = this;
        saveDefaultConfig();
        saveResource("possibleWords.txt", false);

        new Log("Registering events");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerLeaveListener(), this);
        pm.registerEvents(new UnscrambleAttemptListener(), this);
        new Log("Plugin enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        new Log("Plugin disabled");
    }
}
