package me.santiago.kits.commands;

import me.santiago.kits.book.ShopCustomBook;
import me.santiago.kits.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&cOnly command players."));
            return false;
        }

        Player player = (Player) sender;
        player.openInventory(ShopCustomBook.getOpenCustomEnchantBook(player));
        return false;
    }
}
