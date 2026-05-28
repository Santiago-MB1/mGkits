package me.santiago.kits.kit.main;

import me.santiago.kits.Kits;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ListenerManager implements Listener {

    public static void getOpenInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (3 * 9), Color.translate("&4&lGKit Selector"));

        ItemStack normal = new ItemBuilder(Material.getMaterial(351)).setDurability(11).setName(Color.translate("&e&lRegular &7Kits")).build();

        ItemStack special = new ItemBuilder(Material.getMaterial(322)).setDurability(1).setName(Color.translate("&a&dSpecial &7Kits")).build();

        ItemStack vip = new ItemBuilder(Material.getMaterial(406)).setName(Color.translate("&6&lVip &7Kits")).build();

        inventory.setItem( 11, normal);
        inventory.setItem(13, special);
        inventory.setItem(15, vip);

        player.openInventory(inventory);
    }



    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!event.getClickedInventory().getName().startsWith(Color.translate("&4&lGKit Selector"))) return;
        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

        int slot = event.getSlot();

        switch (slot) {
            case 11:
                Kits.getInstance().getKitManager().getOpenInventory(player);
                return;
            case 13:
                Kits.getInstance().getSpecialManager().getOpenInventorySpecial(player);
                return;
            case 15:
                Kits.getInstance().getVipManager().getOpenInventory(player);
        }
    }

}
