package me.santiago.kits.kit.util;

import lombok.Getter;
import lombok.Setter;
import me.santiago.kits.util.StaticUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
@Setter
public class KitData {

    private String name;
    private String permission;
    private int delay;

    private int slot;
    private String color;
    private String item;
    private int durability;

    protected ItemStack[] contents;
    protected ItemStack[] armor;

    public void applyKit(Player target) {
        this.applyNormalKit(target);
    }

    public boolean shouldCancelEvent(int slot) {
        return slot >= 36 && (slot != 45 && slot != 46 && slot != 47 && slot != 48);
    }

    private void applyNormalKit(Player target) {
        for(ItemStack item : this.contents) {
            if(item == null) continue;
            StaticUtils.addToInventoryOrDropToFloor(target, item);
        }

        PlayerInventory inventory = target.getInventory();

        for(int i = 0; i < 4; i++) {
            int index = inventory.getSize() + i;
            ItemStack armorPart = this.armor[i];

            if(inventory.getItem(inventory.getSize() + i) != null) {
                StaticUtils.addToInventoryOrDropToFloor(target, armorPart);
            } else {
                inventory.setItem(index, armorPart);
            }
        }
    }
}