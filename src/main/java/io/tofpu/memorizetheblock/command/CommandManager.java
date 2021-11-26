package io.tofpu.memorizetheblock.command;

import io.tofpu.memorizetheblock.MemorizeTheBlock;
import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    private static CommandManager instance;

    public synchronized static CommandManager of() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private CommandManager() {}

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player command only.");
            return false;
        }
        final Player player = (Player) sender;
        final GameDirector director = MemorizeTheBlock.initialize(null).gameDirector();
        final GameLogicProcess logicProcessor = director.logicProcessor();

        if (logicProcessor.isPlaying(player)) {
            player.kickPlayer("GoodBye!");
        } else {
            logicProcessor.join(player);
        }
        return false;
    }
}
