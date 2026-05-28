package me.santiago.kits.commands;

import me.santiago.kits.Kits;
import me.santiago.kits.kit.util.KitData;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.StaticUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class KitAdminCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&cOnly command players."));
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("kit.permission.admin")) {
            player.sendMessage(Color.translate("&cNo permission"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fAdmin"));
            player.sendMessage(Color.translate(" - &b/akit create <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit remove/delete <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit list <section>"));
            player.sendMessage(Color.translate(" - &b/akit edit <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit delay <section> <kitName> <delay>"));
            player.sendMessage(Color.translate(" - &b/akit color <section> <kitName> <color>"));
            player.sendMessage(Color.translate(" - &b/akit slot <section> <kitName> <slot>"));
            player.sendMessage(Color.translate(" - &b/akit item <section> <kitName> <itemInHand>"));
            player.sendMessage(Color.translate(" - &b/akit apply <section> <kitName> <player>"));
            player.sendMessage(Color.translate(" - &b/akit time <section> <kitName> <player> <value>"));
            player.sendMessage(Color.translate(" - &b/akit data delete"));
            player.sendMessage(Color.translate(" "));
            return false;
        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit create <section> <kitName>"));
                return false;
            }
            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit create normal <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);

                if (kit != null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &calready exists!").replace("<kit>", kit.getName()));
                    return false;
                }

                Kits.getInstance().getKitManager().createKit(args[2]);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &f<kit> &ahas been created.".replace("<kit>", args[2])));

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit create special <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);

                if (kit != null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &calready exists!").replace("<kit>", kit.getName()));
                    return false;
                }

                Kits.getInstance().getSpecialManager().createKit(args[2]);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &f<kit> &ahas been created.".replace("<kit>", args[2])));

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit create vip <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);

                if (kit != null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &calready exists!").replace("<kit>", kit.getName()));
                    return false;
                }

                Kits.getInstance().getVipManager().createKit(args[2]);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &f<kit> &ahas been created.".replace("<kit>", args[2])));

            } else {
                player.sendMessage(Color.translate("&cUsage: /akit create <section> <kitName>"));
            }
        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit remove <section> <kitName>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit remove/delete <section> <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);

                if (kit == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                Kits.getInstance().getKitManager().removeKit(kit);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &c<kit> &chas been removed.".replace("<kit>", args[2])));

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit normal remove/delete <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);

                if (kit == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                Kits.getInstance().getSpecialManager().removeKit(kit);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &c<kit> &chas been removed.".replace("<kit>", args[2])));

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit normal remove/delete <kitName>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);

                if (kit == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                Kits.getInstance().getVipManager().removeKit(kit);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &c<kit> &chas been removed.".replace("<kit>", args[2])));

            } else {
                player.sendMessage(Color.translate("&cUsage: /akit remove/delete <section> <kitName>"));
            }
        } else if (args[0].equalsIgnoreCase("list")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit list <section>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                Kits.getInstance().getKitManager().listKits(player);
            } else if (args[1].equalsIgnoreCase("special")) {
                Kits.getInstance().getSpecialManager().listKits(player);
            } else if (args[1].equalsIgnoreCase("vip")) {
                Kits.getInstance().getVipManager().listKits(player);
            } else {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit list <section>"));
            }
        } else if (args[0].equalsIgnoreCase("edit")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit edit <section> <kitName>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit edit normal <kitName>"));
                    return false;
                }
                String value = Kits.getInstance().getKitManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Kits.getInstance().getKitManager().editKit(player, kit);

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit edit special <kitName>"));
                    return false;
                }
                String value = Kits.getInstance().getSpecialManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Kits.getInstance().getSpecialManager().editKit(player, kit);

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit edit vip <kitName>"));
                    return false;
                }
                String value = Kits.getInstance().getVipManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Kits.getInstance().getVipManager().editKit(player, kit);
            } else {
                player.sendMessage(Color.translate("&cUsage: /akit edit <section> <kitName>"));
            }
        } else if (args[0].equalsIgnoreCase("delay")) {
            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit delay <section> <kitName> <time>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit delay normal <kitName> <delay>"));
                    return false;
                }

                String value = Kits.getInstance().getKitManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Integer delay = StaticUtils.tryParseInteger(args[3]);
                if (delay == null || delay != -1) {
                    delay = StaticUtils.parseSeconds(args[3]);

                    if (delay == -1) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cInvalid number."));
                        return false;
                    }
                }

                kit.setDelay(delay);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &c<kit> &ahas been edit delay <delay>." .replace("<kit>", args[1])
                        .replace("<delay>", DurationFormatUtils.formatDurationWords(delay * 1000L, true, true))));
                return false;
            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit delay special <kitName> <delay>"));
                    return false;
                }

                String value = Kits.getInstance().getSpecialManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Integer delay = StaticUtils.tryParseInteger(args[3]);
                if (delay == null || delay != -1) {
                    delay = StaticUtils.parseSeconds(args[3]);

                    if (delay == -1) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cInvalid number."));
                        return false;
                    }
                }

                kit.setDelay(delay);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &c<kit> &ahas been edit delay <delay>." .replace("<kit>", args[1])
                        .replace("<delay>", DurationFormatUtils.formatDurationWords(delay * 1000L, true, true))));
                return false;

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit delay vip <kitName> <delay>"));
                    return false;
                }

                String value = Kits.getInstance().getVipManager().getKit(args[2]).getName();
                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                Integer delay = StaticUtils.tryParseInteger(args[3]);
                if (delay == null || delay != -1) {
                    delay = StaticUtils.parseSeconds(args[3]);

                    if (delay == -1) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cInvalid number."));
                        return false;
                    }
                }

                kit.setDelay(delay);
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aKit &c<kit> &ahas been edit delay <delay>." .replace("<kit>", args[1])
                        .replace("<delay>", DurationFormatUtils.formatDurationWords(delay * 1000L, true, true))));
                return false;

            } else {
                player.sendMessage(Color.translate("&cUsage: /akit delay <section> <kitName> <time>"));
            }
        } else if (args[0].equalsIgnoreCase("slot")) {
            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit slot <section> <slot>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit slot normal <kitName> <slot>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);
                int item = Integer.parseInt(args[3]);
                String value = Kits.getInstance().getKitManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                try {
                    if (item == 0) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number > 0."));
                        return false;
                    }
                    kit.setSlot(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit slot special <kitName> <slot>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);
                int item = Integer.parseInt(args[3]);
                String value = Kits.getInstance().getSpecialManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                try {
                    if (item == 0) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number > 0."));
                        return false;
                    }
                    kit.setSlot(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit slot vip <kitName> <slot>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);
                int item = Integer.parseInt(args[3]);
                String value = Kits.getInstance().getVipManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }
                try {
                    if (item == 0) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number > 0."));
                        return false;
                    }
                    kit.setSlot(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }
            } else {
                player.sendMessage(Color.translate("&cUsage: /akit slot <section> <kitName> <slot>"));
            }
        } else if (args[0].equalsIgnoreCase("color")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit color <section> <color>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit color normal <kitName> <color>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);
                String item = args[3];

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    if (item == null) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert color."));
                        return false;
                    }
                    kit.setColor(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;
            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit color special <kitName> <color>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);
                String item = args[3];

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    if (item == null) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert color."));
                        return false;
                    }
                    kit.setColor(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;
            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit color vip <kitName> <color>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);
                String item = args[3];

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    if (item == null) {
                        player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert color."));
                        return false;
                    }
                    kit.setColor(item);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the color to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;
            } else {
                player.sendMessage(Color.translate("&cUsage: /akit color <section> <kitName> <color>"));
            }
        } else if (args[0].equalsIgnoreCase("item")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit item <section> <itemInHand>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit item normal <kitName> <itemInHand>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);
                ItemStack item = player.getItemInHand();

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    kit.setItem(item.getType().name());
                    kit.setDurability(item.getDurability());
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the item to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit item special <kitName> <itemInHand>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);
                ItemStack item = player.getItemInHand();

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    kit.setItem(item.getType().name());
                    kit.setDurability(item.getDurability());
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the item to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 3) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit item vip <kitName> <itemInHand>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);
                ItemStack item = player.getItemInHand();

                if (args[2].equalsIgnoreCase(kit.getName())) {
                    kit.setItem(item.getType().name());
                    kit.setDurability(item.getDurability());
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just set the item to the kit &f" + item));
                } else {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                }
                return false;

            } else {
                player.sendMessage(Color.translate(" - &4/akit item <section> <kitName> <itemInHand>"));
            }

        } else if (args[0].equalsIgnoreCase("apply")) {

            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit apply <section> <kitName> <player>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit apply normal <kitName> <player>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getKitManager().getKit(args[2]);
                Player target = Bukkit.getPlayer(args[3]);
                String value = Kits.getInstance().getKitManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Kits.getInstance().getKitManager().giveKitWithCommand(player, target, kit);
                return false;

            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit apply special <kitName> <player>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getSpecialManager().getKit(args[2]);
                Player target = Bukkit.getPlayer(args[3]);
                String value = Kits.getInstance().getSpecialManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Kits.getInstance().getSpecialManager().giveKitWithCommand(player, target, kit);
                return false;

            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 4) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit apply vip <kitName> <player>"));
                    return false;
                }

                KitData kit = Kits.getInstance().getVipManager().getKit(args[2]);
                Player target = Bukkit.getPlayer(args[3]);
                String value = Kits.getInstance().getVipManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(value)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Kits.getInstance().getVipManager().giveKitWithCommand(player, target, kit);
                return false;
            } else {
                player.sendMessage(Color.translate("&4Usage: /akit apply <section> <kitName> <player>"));
            }
        } else if (args[0].equalsIgnoreCase("time")) {
            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit time <section> <kitName> <value>"));
                return false;
            }

            if (args[1].equalsIgnoreCase("normal")) {
                if (args.length < 5) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit time normal <kitName> <player> <value>"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[3]);
                String tick = Kits.getInstance().getKitManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(tick)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Long value = StaticUtils.tryParseLong(args[4]);

                try {
                    Kits.getInstance().getUserdataManager().getUserdata(target.getUniqueId()).getNormalKitDelays().put(tick, value);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou has reset cooldown target &f" + tick));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }
                return false;
            } else if (args[1].equalsIgnoreCase("special")) {
                if (args.length < 5) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit time special <kitName> <player> <value>"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[3]);
                String tick = Kits.getInstance().getSpecialManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(tick)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Long value = StaticUtils.tryParseLong(args[4]);

                try {
                    Kits.getInstance().getUserdataManager().getUserdata(target.getUniqueId()).getNormalKitDelays().put(tick, value);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou has reset cooldown target &f" + tick));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }
                return false;
            } else if (args[1].equalsIgnoreCase("vip")) {
                if (args.length < 5) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit time vip <kitName> <player> <value>"));
                    return false;
                }

                Player target = Bukkit.getPlayer(args[3]);
                String tick = Kits.getInstance().getVipManager().getKit(args[2]).getName();

                if (!args[2].equalsIgnoreCase(tick)) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cKit &f<kit> &cno exist!").replace("<kit>", args[2]));
                    return false;
                }

                if (target == null) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls target player."));
                    return false;
                }

                Long value = StaticUtils.tryParseLong(args[4]);

                try {
                    Kits.getInstance().getUserdataManager().getUserdata(target.getUniqueId()).getNormalKitDelays().put(tick, value);
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou has reset cooldown target &f" + tick));
                    return false;
                } catch (NumberFormatException e) {
                    player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cPls insert number correct."));
                }
                return false;
            } else {
                player.sendMessage(Color.translate("&cUsage: /akit time <section> <kitName> <player> <value>"));
            }
        } else if (args[0].equalsIgnoreCase("data")) {
            if (args.length < 2) {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit data delete"));
                return false;
            }

            if ("delete".equalsIgnoreCase(args[1])) {
                Kits.getInstance().getUserdataManager().deleteAllUserdata();
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou have just deleted the entire user record."));
                return false;
            } else {
                player.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &cUsage: /akit data delete"));
            }
        } else {
            player.sendMessage(Color.translate(" "));
            player.sendMessage(Color.translate(" &b&lmGkits &7┃ &fAdmin"));
            player.sendMessage(Color.translate(" - &b/akit create <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit remove/delete <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit list <section>"));
            player.sendMessage(Color.translate(" - &b/akit edit <section> <kitName>"));
            player.sendMessage(Color.translate(" - &b/akit delay <section> <kitName> <delay>"));
            player.sendMessage(Color.translate(" - &b/akit color <section> <kitName> <color>"));
            player.sendMessage(Color.translate(" - &b/akit slot <section> <kitName> <slot>"));
            player.sendMessage(Color.translate(" - &b/akit item <section> <kitName> <itemInHand>"));
            player.sendMessage(Color.translate(" - &b/akit apply <section> <kitName> <player>"));
            player.sendMessage(Color.translate(" - &b/akit time <section> <kitName> <player> <value>"));
            player.sendMessage(Color.translate(" - &b/akit data delete"));
            player.sendMessage(Color.translate(" "));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Color.getCompletions(args, Arrays.asList("create", "remove", "delete", "list", "edit", "delay", "color", "slot", "item", "apply", "time", "data"));
        }

        if (args.length == 2) {
            return Color.getCompletions(args, Arrays.asList("vip", "special", "normal"));
        }

        if (args[1].equalsIgnoreCase("normal") && args.length == 3) {
            return Color.getCompletions(args, Kits.getInstance().getKitManager().getKits());
        }

        if (args[1].equalsIgnoreCase("vip") && args.length == 3) {
            return Color.getCompletions(args, Kits.getInstance().getVipManager().getKits());
        }

        if (args[1].equalsIgnoreCase("special") && args.length == 3) {
            return Color.getCompletions(args, Kits.getInstance().getSpecialManager().getKits());
        }

        return null;
    }
}
