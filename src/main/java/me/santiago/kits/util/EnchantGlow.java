package me.santiago.kits.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class EnchantGlow extends EnchantmentWrapper
{
    private static Enchantment glow;

    public EnchantGlow(final int id) {
        super(id);
    }

    public boolean canEnchantItem(final ItemStack item) {
        return true;
    }

    public boolean conflictsWith(final Enchantment other) {
        return false;
    }

    public EnchantmentTarget getItemTarget() {
        return null;
    }

    public int getMaxLevel() {
        return 10;
    }

    public String getName() {
        return "Glow";
    }

    public int getStartLevel() {
        return 1;
    }

    public static Enchantment getGlow() {
        if (EnchantGlow.glow != null) {
            return EnchantGlow.glow;
        }
        Enchantment[] values;
        for (int length = (values = Enchantment.values()).length, i = 0; i < length; ++i) {
            final Enchantment ench = values[i];
            if (new StringBuilder().append(ench).toString().toLowerCase().contains("glow")) {
                return EnchantGlow.glow = ench;
            }
        }
        try {
            final Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Enchantment.registerEnchantment(EnchantGlow.glow = (Enchantment)new EnchantGlow(169));
        return EnchantGlow.glow;
    }

    public static ItemStack addGlow(final ItemStack item) {
        final Enchantment glow = getGlow();
        item.addEnchantment(glow, 1);
        return item;
    }
}