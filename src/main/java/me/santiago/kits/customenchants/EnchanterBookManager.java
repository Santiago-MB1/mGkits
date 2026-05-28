package me.santiago.kits.customenchants;

import me.santiago.kits.Kits;
import me.santiago.kits.book.ShopCustomBook;
import me.santiago.kits.customenchants.event.ArmorEquipEvent;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchanterBookManager implements Listener {

    public EnchanterBookManager() {
        Bukkit.getPluginManager().registerEvents(this, Kits.getInstance());
        Bukkit.getPluginManager().registerEvents(new ShopCustomBook(), Kits.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (ArmorType.match(item)) {
            if (item.getType() != Material.AIR && event.getCursor() != null && isEffectBook(event.getCursor())) {
                event.setCancelled(true);

                ItemStack newItem = setNewItem(event.getCursor(), item);
                ItemStack oldItem = new ItemStack(Material.AIR);

                player.sendMessage(Color.translate("&b&lmGkits &7┃ &aYou have just upgraded armor."));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                event.setCurrentItem(newItem);

                player.setItemOnCursor(new ItemStack(Material.AIR));

                if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                    ArmorEquipEvent ev = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.DRAG, ArmorType.matchType(item), oldItem, newItem);
                    Bukkit.getPluginManager().callEvent(ev);
                }
            }
        }
    }

    private ItemStack setNewItem(ItemStack book, ItemStack current) {
        List<String> lores = new ArrayList<>();

        ItemMeta meta = current.getItemMeta();
        ItemMeta metaBook = book.getItemMeta();
        if (meta != null && meta
                .hasLore()) {
            lores.addAll(meta.getLore());
        }
        lores.addAll(metaBook.getLore());
        List<String> newLore = new ArrayList<>(lores);
        if (meta != null) {
            meta.setLore(newLore);
            current.setItemMeta(meta);
        } else {
            metaBook.setLore(newLore);
            current.setItemMeta(metaBook);
        }

        return current;
    }

    private boolean isEffectBook(ItemStack book) {
        if (book.getType() == Material.ENCHANTED_BOOK) {
            for (String s : Kits.getInstance().getCustomEnchantManager().getEnchants()) {
                for (String lines : book.getItemMeta().getLore()) {
                    if (ChatColor.stripColor(lines.toLowerCase()).startsWith(s.toLowerCase().replace("_", " "))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ItemStack getBook(String enchant) {
        return new ItemBuilder(Material.ENCHANTED_BOOK).setName(Color.translate("&c" + enchant)).setLore("", Color.translate("&7Interact in your armor!")).build();
    }

}
