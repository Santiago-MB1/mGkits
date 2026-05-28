package me.santiago.kits.book;

import me.santiago.kits.Kits;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopCustomBook implements Listener {

    public static Inventory getOpenCustomEnchantBook(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Book Enchants Shop");

        ItemStack basic = new ItemBuilder(Material.BOOK).setName(Color.translate("&9&lBasic &7Enchants")).build();
        ItemStack ultimate = new ItemBuilder(Material.BOOK).setName(Color.translate("&6&lUltimate &7Enchants")).build();

        inventory.setItem(11, basic);
        inventory.setItem(15,ultimate);

        return inventory;
    }

    @EventHandler
    public void onInteractivePrincipalEnchantBook(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (event.getInventory().getName().equals("Book Enchants Shop")) {
            if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) {
                return;
            }
            event.setCancelled(true);
            switch (slot) {
                case 11:
                    player.openInventory(getOpenCustomEnchantBookBasic(player));
                    return;

                case 15:
                    player.openInventory(getOpenCustomEnchantBookUltimate(player));
            }
        }
    }


    private Inventory getOpenCustomEnchantBookBasic(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Shop (Basic)");

        ItemStack speed = new ItemBuilder(Material.BOOK).setName(Color.translate("&bSpeed &7(II)")).setLore(Color.translate("&7Cost: &e60LVL EXP")).build();
        ItemStack fire_resistance = new ItemBuilder(Material.BOOK).setName(Color.translate("&6Fire Resistance &7(I)")).setLore(Color.translate("&7Cost: &e80LVL EXP")).build();
        ItemStack water_breathing = new ItemBuilder(Material.BOOK).setName(Color.translate("&9Water Breathing &7(I)")).setLore(Color.translate("&7Cost: &e75LVL EXP" )).build();
        ItemStack night_vision = new ItemBuilder(Material.BOOK).setName(Color.translate("&1Night Vision &7(I)")).setLore(Color.translate("&7Cost: &e50LVL EXP")).build();

        inventory.setItem(10, speed);
        inventory.setItem(12, fire_resistance);
        inventory.setItem(14, water_breathing);
        inventory.setItem(16, night_vision);

        return inventory;
    }

    @EventHandler
    public void onInteractiveBasicEnchantBook(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (event.getInventory().getName().equals("Shop (Basic)")) {
            if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) {
                return;
            }
            event.setCancelled(true);
            switch (slot) {
                case 10:
                    if (player.getLevel() < 60 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }

                    player.closeInventory();
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&bSpeed II"));
                    player.setLevel(player.getLevel() - 60);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;

                case 12:
                    if (player.getLevel() < 80 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&6Fire Resistance I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 80);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;

                case 14:
                    if (player.getLevel() < 75 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&9Water Breathing I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 75);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;
                case 16:
                    if (player.getLevel() < 50 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&8Night Vision I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 50);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
            }
        }
    }



    private Inventory getOpenCustomEnchantBookUltimate(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "Shop (Ultimate)");

        ItemStack invisibility = new ItemBuilder(Material.BOOK).setName(Color.translate("&8Invisibility &7(I)")).setLore(Color.translate("&7Cost: &e75LVL EXP")).build();
        ItemStack implants = new ItemBuilder(Material.BOOK).setName(Color.translate("&cImplants &7(I)")).setLore(Color.translate("&7Cost: &e100LVL EXP")).build();
        ItemStack hell_forged = new ItemBuilder(Material.BOOK).setName(Color.translate("&cHell Forged &7(II)")).setLore(Color.translate("&7Cost: &e100LVL EXP")).build();
        ItemStack recover = new ItemBuilder(Material.BOOK).setName(Color.translate("&cRecover &7(I)")).setLore(Color.translate("&7Cost: &e100LVL EXP")).build();

        inventory.setItem(10, invisibility);
        inventory.setItem(12, implants);
        inventory.setItem(14, hell_forged);
        inventory.setItem(16, recover);

        return inventory;
    }

    @EventHandler
    public void onInteractiveUltimateEnchantBook(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (event.getInventory().getName().equals("Shop (Ultimate)")) {
            if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) {
                return;
            }
            event.setCancelled(true);
            switch (slot) {
                case 10:
                    if (player.getLevel() < 75 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&aInvisibility I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 75);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;

                case 12:
                    if (player.getLevel() < 100 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&cImplants I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 100);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;

                case 14:
                    if (player.getLevel() < 100 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&cHell Forged I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 100);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
                    return;
                case 16:
                    if (player.getLevel() < 100 && !player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(Color.translate("&cYou need exp to be able to acquire this enchantment."));
                        return;
                    }
                    player.getInventory().addItem(Kits.getInstance().getEnchanterBook().getBook("&cRecover I"));
                    player.closeInventory();
                    player.setLevel(player.getLevel() - 100);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
            }
        }
    }







}
