package me.grovre.afkunscramble.listeners;

import me.grovre.afkunscramble.AfkUnscrambleAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class PlayerMoveListener implements Listener {

    public void playerMovementListener(PlayerMoveEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        AfkUnscrambleAPI.restartAfkTimer(uuid);
    }
}
