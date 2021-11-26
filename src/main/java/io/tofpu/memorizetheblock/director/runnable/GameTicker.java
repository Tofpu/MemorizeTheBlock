package io.tofpu.memorizetheblock.director.runnable;

import io.tofpu.memorizetheblock.plugin.MemorizeTheBlockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class GameTicker implements Runnable {
    protected BukkitTask task;

    @Override
    public abstract void run();

    public void resume() {
        this.task = Bukkit.getScheduler().runTaskTimer(MemorizeTheBlockPlugin.getPlugin(MemorizeTheBlockPlugin.class), this, 1L, 20);
    }

    public void pause() {
        if (task == null) return;

        this.task.cancel();
        this.task = null;
    }

    public boolean isPaused() {
        return task == null;
    }

    public BukkitTask task() {
        return this.task;
    }
}
