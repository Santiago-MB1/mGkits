package me.santiago.kits.commands;

import me.santiago.kits.kit.main.ListenerManager;
import me.santiago.kits.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&cOnly command players."));
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            ListenerManager.getOpenInventory(player);
        } else {
            player.sendMessage(Color.translate("&cUsage: /gkit"));
        }

        return false;
    }
}
