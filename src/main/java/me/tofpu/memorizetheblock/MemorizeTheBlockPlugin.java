package me.tofpu.memorizetheblock;

import me.tofpu.memorizetheblock.command.CommandManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MemorizeTheBlockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Metrics(this, 12787);
        MemorizeTheBlock.initialize(this);

        getCommand("memorize").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
