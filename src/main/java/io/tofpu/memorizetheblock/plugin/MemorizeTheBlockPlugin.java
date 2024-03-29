package io.tofpu.memorizetheblock.plugin;

import io.tofpu.memorizetheblock.MemorizeTheBlock;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MemorizeTheBlockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Metrics(this, 12787);;

        MemorizeTheBlock.initialize(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
