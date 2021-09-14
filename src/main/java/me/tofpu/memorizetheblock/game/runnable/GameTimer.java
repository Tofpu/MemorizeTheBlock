package me.tofpu.memorizetheblock.game.runnable;

import me.tofpu.memorizetheblock.game.object.GamePlayer;
import me.tofpu.memorizetheblock.game.processor.BlockProspectProcessor;
import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import me.tofpu.memorizetheblock.util.GameUtil;
import org.bukkit.Sound;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameTimer extends GameTicker {
    private final GameLogicProcessor logicProcessor;
    private final BlockProspectProcessor blockProspectProcessor;

    public GameTimer(final GameLogicProcessor logicProcessor, final BlockProspectProcessor blockProspectProcessor) {
        this.logicProcessor = logicProcessor;
        this.blockProspectProcessor = blockProspectProcessor;
    }

    @Override
    public void run() {
        final CopyOnWriteArrayList<GamePlayer> players = new CopyOnWriteArrayList<>(logicProcessor.players());
        for (final GamePlayer gamePlayer : players) {
            final Instant start = logicProcessor.playerDuration().get(gamePlayer);
            if (start == null) continue;
            final Duration duration = Duration.between(start, Instant.now());

            // they reached the 6 seconds period
            if (duration.getSeconds() >= 6) {
                blockProspectProcessor.result(logicProcessor, gamePlayer);
            } else {
                GameUtil.playSound(gamePlayer, Sound.CLICK);
            }
        }
    }

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }
}
