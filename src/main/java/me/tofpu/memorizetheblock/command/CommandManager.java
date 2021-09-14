package me.tofpu.memorizetheblock.command;

import me.tofpu.memorizetheblock.MemorizeTheBlock;
import me.tofpu.memorizetheblock.game.GameDirector;
import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player command only.");
            return false;
        }
        final Player player = (Player) sender;
        final GameDirector director = MemorizeTheBlock.initialize(null).gameDirector();
        final GameLogicProcessor logicProcessor = director.logicProcessor();

        if (logicProcessor.isPlaying(player)) {
            player.kickPlayer("GoodBye!");
        } else {
            logicProcessor.join(player);
        }
        return false;
    }
}
