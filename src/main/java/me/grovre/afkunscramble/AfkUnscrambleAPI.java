package me.grovre.afkunscramble;

import me.grovre.afkunscramble.game.UnscramblerGame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AfkUnscrambleAPI {

    public static ConcurrentHashMap<UUID, BukkitTask> playerAfkTasks = new ConcurrentHashMap<>();

    public static void startAfk(@Nonnull UUID uuid) {
        long timeBeforePrompt = AfkUnscramble.plugin.getConfig().getLong("TimeBeforeAfkTrigger");

        BukkitTask countdownTask = Bukkit.getScheduler().runTaskLater(
                AfkUnscramble.plugin,
                () -> AfkUnscrambleAPI.promptAfkUnscramble(uuid),
                timeBeforePrompt
        );

        playerAfkTasks.put(uuid, countdownTask);
    }

    public static void promptAfkUnscramble(UUID uuid) {
        FileConfiguration config = AfkUnscramble.plugin.getConfig();
        String kickMessage = config.getString("KickMessage");
        long delayBeforeKick = config.getLong("TimeToUnscramble");
        Player player = Bukkit.getPlayer(uuid);
        assert player != null;

        BukkitTask promptedCountdownTask = Bukkit.getScheduler().runTaskLater(
                AfkUnscramble.plugin,
                () -> promptFailed(uuid, kickMessage),
                delayBeforeKick
        );

        new UnscramblerGame(player);
        playerAfkTasks.put(uuid, promptedCountdownTask);
    }

    public static void promptFailed(UUID uuid, String kickMessage) {
        Player player = Bukkit.getPlayer(uuid);
        assert player != null;

        player.kickPlayer(kickMessage);
        playerAfkTasks.remove(uuid);
    }

    @Nonnull
    public static String getRandomWord() throws IOException {
        File wordFile = new File(AfkUnscramble.plugin.getDataFolder().getAbsolutePath()
                + File.separator + "possibleWords.txt");
        if(!wordFile.exists()) {
            AfkUnscramble.plugin.saveResource(wordFile.getAbsolutePath(), true);
        }

        BufferedReader br = new BufferedReader(new FileReader(wordFile));
        String word = br.lines().findAny().orElse("supercalifragilisticexpialidocious");

        return word.toLowerCase();
    }

    @Nonnull
    public static String scrambleString(String str) {
        if(str.length() <= 1) return str;
        char[] chars = str.toCharArray();
        Random r = new Random();

        for(int i = 0; i < chars.length; i++) {
            int randomIndex;
            do {
                randomIndex = r.nextInt(chars.length);
            } while(randomIndex == i);
            char temp = chars[randomIndex];
            chars[randomIndex] = chars[i];
            chars[i] = temp;
        }

        return new String(chars);
    }

    public static boolean playerHasPrompt(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return false;
        return playerHasPrompt(player);
    }

    public static boolean playerHasPrompt(Player player) {
        return UnscramblerGame.playersWithGames.containsKey(player);
    }

    public static void restartAfkTimer(Player player) {
        UUID uuid = player.getUniqueId();
        restartAfkTimer(uuid);
    }

    public static void restartAfkTimer(UUID uuid) {
        BukkitTask promptTask = playerAfkTasks.get(uuid);
        if(promptTask != null) {
            promptTask.cancel();
        }
        startAfk(uuid);
    }
}
