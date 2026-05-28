package me.santiago.kits.commands;

import me.santiago.kits.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddEnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&cOnly command players."));
            return false;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("kit.permission.admin")) {
            player.sendMessage(Color.translate("&cNo permission."));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fEnchant admin"));
            player.sendMessage(Color.translate(" - &b/customenchant apply <enchant>"));
            player.sendMessage(Color.translate(" - &b/customenchant list"));
            player.sendMessage(Color.translate(" "));
            return true;
        }

        if (args[0].equalsIgnoreCase("apply")) {
            if (args[1].equalsIgnoreCase("Speed")) {

                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&bSpeed II";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));
            } else if (args[1].equalsIgnoreCase("FireResistance")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&6Fire Resistance I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));

            } else if (args[1].equalsIgnoreCase("WaterBreathing")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&9Water Breathing I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));
            } else if (args[1].equalsIgnoreCase("Invisibility")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&8Invisibility I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));

            } else if (args[1].equalsIgnoreCase("NightVision")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&1Night Vision I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));

            } else if (args[1].equalsIgnoreCase("Implants")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&cImplants I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));
            } else if (args[1].equalsIgnoreCase("Hellforged")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&cHell Forged I" ;
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));
            } else if (args[1].equalsIgnoreCase("Recover")) {
                ItemStack stack = player.getItemInHand();

                if (stack == null) {
                    sender.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &cYou must hold item."));
                    return true;
                }

                ItemMeta meta = stack.getItemMeta();
                String enchant = "&cRecover I";
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

                lore.add(Color.translate(enchant));
                meta.setLore(lore);

                stack.setItemMeta(meta);
                player.sendMessage(Color.translate("&b&lmGkits &f(ENCHANT) &7┃ &aYou have successfully added lore. Check item!"));
            } else {
                player.sendMessage(Color.translate(" "));
                player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fEnchant List"));
                player.sendMessage(Color.translate("- &bSpeed"));
                player.sendMessage(Color.translate("- &6FireResistance"));
                player.sendMessage(Color.translate("- &9WaterBreathing"));
                player.sendMessage(Color.translate("- &8Invisibility"));
                player.sendMessage(Color.translate("- &1NightVision"));
                player.sendMessage(Color.translate("- &cImplants"));
                player.sendMessage(Color.translate("- &cHellforged"));
                player.sendMessage(Color.translate("- &cRecover"));
                player.sendMessage(Color.translate(" "));
            }
            return false;
        } else if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fEnchant List"));
            player.sendMessage(Color.translate("- &bSpeed"));
            player.sendMessage(Color.translate("- &6FireResistance"));
            player.sendMessage(Color.translate("- &9WaterBreathing"));
            player.sendMessage(Color.translate("- &8Invisibility"));
            player.sendMessage(Color.translate("- &1NightVision"));
            player.sendMessage(Color.translate("- &cImplants"));
            player.sendMessage(Color.translate("- &cHellforged"));
            player.sendMessage(Color.translate("- &cRecover"));
            player.sendMessage(Color.translate(" "));
            return false;
        } else {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fEnchant admin"));
            player.sendMessage(Color.translate(" - &b/customenchant apply <enchant>"));
            player.sendMessage(Color.translate(" - &b/customenchant remove <lore-number>"));
            player.sendMessage(Color.translate(" - &b/customenchant list"));
            player.sendMessage(Color.translate(" "));
        }
        return false;
    }

}
