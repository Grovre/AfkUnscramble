package me.grovre.afkunscramble.listeners;

import me.grovre.afkunscramble.AfkUnscramble;
import me.grovre.afkunscramble.AfkUnscrambleAPI;
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
        removeFromAfkList(event.getPlayer());
    }

    @EventHandler
    public void playerDisconnectListener(PlayerQuitEvent event) {
        removeFromAfkList(event.getPlayer());
    }

    private void removeFromAfkList(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            AfkUnscrambleAPI.playerAfkTasks.remove(uuid).cancel();
        } catch (Exception ignored) {}
    }
}
