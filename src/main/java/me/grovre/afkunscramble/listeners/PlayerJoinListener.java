package me.grovre.afkunscramble.listeners;

import me.grovre.afkunscramble.AfkUnscrambleAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void PlayerJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AfkUnscrambleAPI.restartAfkTimer(player);
    }
}
