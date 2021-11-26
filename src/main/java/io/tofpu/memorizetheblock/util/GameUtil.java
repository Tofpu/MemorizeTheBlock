package io.tofpu.memorizetheblock.util;

import io.tofpu.memorizetheblock.director.object.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameUtil {
    public static void playSound(final GamePlayer gamePlayer, final Sound sound) {
        final Player player = gamePlayer.player();

        player.playSound(player.getLocation(), sound, 1F, 1F);
    }

    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
