package io.tofpu.memorizetheblock.director.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class GameListener {
    private static final List<Listener> listeners = new ArrayList<>();

    private GameListener() {}

    public static void register(final Listener listener) {
        listeners.add(listener);
    }

    public static void initialize(final Plugin plugin) {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (final Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
