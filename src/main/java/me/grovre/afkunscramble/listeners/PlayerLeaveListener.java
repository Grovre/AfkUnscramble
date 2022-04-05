package me.grovre.afkunscramble.listeners;

import me.grovre.afkunscramble.AfkUnscramble;
import me.grovre.afkunscramble.AfkUnscrambleAPI;
import me.grovre.afkunscramble.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void playerKickListener(PlayerKickEvent event) {
        Player player = event.getPlayer();
        new Log("Player kicked: " + player.getName());
        removeFromAfkList(player);
    }

    @EventHandler
    public void playerDisconnectListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        new Log("Player disconnected: " + player.getName());
        removeFromAfkList(player);
    }

    private void removeFromAfkList(Player player) {
        UUID uuid = player.getUniqueId();
        new Log("Player removed from AfkUnscramble list: " + player.getName() + " (" + uuid + ")");
        try {
            AfkUnscrambleAPI.playerAfkTasks.remove(uuid).cancel();
        } catch (Exception ignored) {}
    }
}
