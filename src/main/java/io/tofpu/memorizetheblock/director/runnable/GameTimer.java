package io.tofpu.memorizetheblock.director.runnable;

import io.tofpu.memorizetheblock.director.object.GamePlayer;
import io.tofpu.memorizetheblock.director.process.BlockProspectProcess;
import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import io.tofpu.memorizetheblock.util.GameUtil;
import org.bukkit.Sound;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameTimer extends GameTicker {
    private final GameLogicProcess logicProcessor;
    private final BlockProspectProcess blockProspectProcess;

    public static GameTimer of(final GameLogicProcess logicProcessor, final BlockProspectProcess blockProspectProcess) {
        return new GameTimer(logicProcessor, blockProspectProcess);
    }

    public GameTimer(final GameLogicProcess logicProcessor, final BlockProspectProcess blockProspectProcess) {
        this.logicProcessor = logicProcessor;
        this.blockProspectProcess = blockProspectProcess;
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
                this.blockProspectProcess.result(logicProcessor, gamePlayer);
            } else {
                GameUtil.playSound(gamePlayer, Sound.CLICK);
            }
        }
    }

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }
}
