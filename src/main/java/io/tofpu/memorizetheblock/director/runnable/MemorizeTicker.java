package io.tofpu.memorizetheblock.director.runnable;

import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.director.object.GamePlayer;
import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import io.tofpu.memorizetheblock.util.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.time.Instant;

public class MemorizeTicker extends GameTicker {
    public static void start(final GameDirector director, final GamePlayer gamePlayer) {
        new MemorizeTicker(director, gamePlayer).resume();
    }

    private final GameDirector director;
    private final GamePlayer gamePlayer;

    public MemorizeTicker(final GameDirector director, final GamePlayer gamePlayer) {
        this.director = director;
        this.gamePlayer = gamePlayer;
    }

    @Override
    public void resume() {
        super.task = Bukkit.getScheduler().runTaskLater(director.plugin(), this, 20 * 5);
    }

    @Override
    public void run() {
        final GameLogicProcess logicProcessor = director.logicProcessor();

        for (int i = 0; i < 3; i++) {
            director.isolatedWorldLogic().isolate(gamePlayer.player().getUniqueId()).chosenBlocks()[i].block(Material.AIR);
        }
        if (logicProcessor.timer().isPaused()) logicProcessor.timer().resume();
        logicProcessor.playerDuration().put(gamePlayer, Instant.now());

        gamePlayer.popItem();
        gamePlayer.player().sendMessage(GameUtil.colorize("&ePlace your blocks now. You got 5 seconds!!!"));
    }
}
