package me.tofpu.memorizetheblock.game.runnable;

import me.tofpu.memorizetheblock.MemorizeTheBlockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class GameTicker implements Runnable {
    protected BukkitTask task;

    @Override
    public abstract void run();

    public void resume() {
        task = Bukkit.getScheduler().runTaskTimer(MemorizeTheBlockPlugin.getPlugin(MemorizeTheBlockPlugin.class), this, 1L, 20);
    }

    public void pause() {
        if (task == null) return;

        task.cancel();
        task = null;
    }

    public boolean isPaused() {
        return task == null;
    }

    public BukkitTask task() {
        return this.task;
    }
}
