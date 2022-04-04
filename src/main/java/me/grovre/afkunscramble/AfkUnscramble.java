package me.grovre.afkunscramble;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AfkUnscramble extends JavaPlugin {

    public static AfkUnscramble plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();

        // TODO: 4/4/22 Save word list as resource 
        // TODO: 4/4/22 Refactor to work on a timer instead of player movement
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
