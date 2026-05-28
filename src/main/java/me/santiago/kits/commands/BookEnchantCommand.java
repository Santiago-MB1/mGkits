package me.santiago.kits.commands;

import me.santiago.kits.Kits;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.StaticUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BookEnchantCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&cOnly command players."));
            return false;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fBook Enchant admin"));
            player.sendMessage(Color.translate(" - &b/bookenchant <enchant> <level>"));
            player.sendMessage(Color.translate(" "));
            return true;
        }

        if (args.length == 1) {
            player.sendMessage(Color.translate("&cValue not valid!"));
            return true;
        }

        if (Integer.parseInt(args[1]) < 0) {
            player.sendMessage(Color.translate("&cThe level mush be positive!"));
            return false;
        }

        String enchants = Kits.getInstance().getCustomEnchantManager().getEnchant(args[0]);

        if (enchants != null) {
            if (StaticUtils.isInvFull(player)) {
                player.getWorld().dropItemNaturally(player.getLocation(), Kits.getInstance().getEnchanterBook().getBook(enchants));
            }
            else {
                player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook(enchants));
            }
        }
        else {
            player.sendMessage(Color.translate("&cEnchanter not found."));
        }
        return false;
    }
}
