package me.grovre.afkunscramble.game;

import me.grovre.afkunscramble.AfkUnscramble;
import me.grovre.afkunscramble.AfkUnscrambleAPI;
import me.grovre.afkunscramble.Log;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;

public class UnscramblerGame {

    public static HashMap<Player, UnscramblerGame> playersWithGames = new HashMap<>();

    private String word;
    private String scrambledWord;
    private final WordHints hints;
    private final Player player;

    public UnscramblerGame(Player player) {
        try {
            this.word = AfkUnscrambleAPI.getRandomWord();
            word = word.toLowerCase();
            scrambledWord = AfkUnscrambleAPI.scrambleString(word);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert word != null;
        hints = new WordHints(word);
        this.player = player;
        playersWithGames.put(player, this);

        long secondsToUnscramble = AfkUnscramble.plugin.getConfig().getLong("TimeToUnscramble");
        player.sendMessage(ChatColor.RED + "You have been AFK for too long!");
        player.sendMessage(ChatColor.RED + "You have "
                + ((int) (secondsToUnscramble/1000))
                + " seconds to unscramble the word: "
                + ChatColor.YELLOW + scrambledWord);

        new Log("Unscramble game started: " + player.getName());
    }

    public void makeUnscrambleAttempt(String attempt) {
        attempt = attempt.toLowerCase();
        if(attempt.equals(word)) {
            playersWithGames.remove(player);
            AfkUnscrambleAPI.restartAfkTimer(player);
            player.sendMessage(ChatColor.GREEN + "Nice");
            new Log("Successful unscramble attempt: " + player.getName());
            return;
        }
        player.sendMessage(ChatColor.RED + "Incorrect guess.");
        if(AfkUnscramble.plugin.getConfig().getBoolean("OfferHints")) {
            int lettersToReveal = AfkUnscramble.plugin.getConfig().getInt("LettersRevealedPerHint");
            giveHint(lettersToReveal);
        }
        new Log("Unsuccessful unscramble attempt: " + player.getName());
    }

    public String giveHint(int amountOfLettersToGive) {
        new Log("Hint given: " + player.getName());
        return hints.progressHints(amountOfLettersToGive);
    }
}
