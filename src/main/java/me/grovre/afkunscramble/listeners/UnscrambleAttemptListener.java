package me.grovre.afkunscramble.listeners;

import me.grovre.afkunscramble.AfkUnscrambleAPI;
import me.grovre.afkunscramble.game.UnscramblerGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class UnscrambleAttemptListener implements Listener {

    @EventHandler
    public void playerUnscrambleAttempt(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!AfkUnscrambleAPI.playerHasPrompt(player)) return;

        String message = event.getMessage();
        AfkUnscrambleAPI.getPlayerUnscramblerGame(player);
    }
}
