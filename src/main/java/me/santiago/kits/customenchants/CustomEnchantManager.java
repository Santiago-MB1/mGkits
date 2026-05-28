package me.santiago.kits.customenchants;

import me.santiago.kits.Kits;
import me.santiago.kits.customenchants.event.ArmorEquipEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchantManager implements Listener {

    @Getter public List<String> enchants =  new ArrayList<>();

    public CustomEnchantManager() {

        this.load();
        Bukkit.getPluginManager().registerEvents(this, Kits.getInstance());
    }

    private void load() {
        PotionEffectType[] enchantsPotions = PotionEffectType.values();

        if (enchants.isEmpty()) {
            for (PotionEffectType type : enchantsPotions) {
                if (type != null) {
                    enchants.add(type.getName());
                    enchants.add("Implants");
                    enchants.add("Recover");
                    enchants.add("Hell Forged");
                }
            }
        }
    }

    public String getEnchant(String enchant) {
        return this.enchants.stream().filter( enchants -> enchants.equalsIgnoreCase(enchant)).findFirst().orElse(null);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorEquip(ArmorEquipEvent event) {
        ItemStack oldPiece = event.getOldArmorPiece();
        ItemStack newPiece = event.getNewArmorPiece();
        if (oldPiece != null && oldPiece.hasItemMeta() && oldPiece.getItemMeta().hasLore()) {
            event.getPlayer().getActivePotionEffects().forEach(types -> {
                if (types.getDuration() > 300) {
                    for (String s : this.enchants) {
                        for (String lines : oldPiece.getItemMeta().getLore()) {
                            if (ChatColor.stripColor(lines.toLowerCase()).startsWith(s.toLowerCase().replace("_", " "))) {
                                String[] split = lines.split(" ");
                                String potion = ChatColor.stripColor(split[0]);
                                if (split.length == 3) {
                                    potion = ChatColor.stripColor(split[0]) + "_" + ChatColor.stripColor(split[1]);
                                }
                                try {
                                    event.getPlayer().removePotionEffect(PotionEffectType.getByName(potion));
                                } catch (IllegalArgumentException | NullPointerException ignored) {}
                            }
                        }
                    }
                }
            });
        }
        if (newPiece != null && newPiece.hasItemMeta() && newPiece.getItemMeta().hasLore()) {
            try {
                for (String s : this.enchants) {
                    for (String lines : newPiece.getItemMeta().getLore()) {
                        if (ChatColor.stripColor(lines.toLowerCase()).startsWith(s.toLowerCase().replace("_", " "))) {
                            String[] split = lines.split(" ");
                            String potion = ChatColor.stripColor(split[0]);
                            int level = this.getLevel(ChatColor.stripColor(split[1]));
                            if (split.length == 3) {
                                potion = ChatColor.stripColor(split[0]) + "_" + ChatColor.stripColor(split[1]);
                                level = this.getLevel(ChatColor.stripColor(split[2]));
                            }

                            try {
                                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(potion), 2147483647, level - 1));

                            } catch (IllegalArgumentException ignored) {}
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }
    }

    public Integer getLevel(String roman) {
        if (roman.equalsIgnoreCase("I"))
            return Integer.valueOf(1);
        if (roman.equalsIgnoreCase("II"))
            return Integer.valueOf(2);
        if (roman.equalsIgnoreCase("III"))
            return Integer.valueOf(3);
        if (roman.equalsIgnoreCase("IV"))
            return Integer.valueOf(4);
        if (roman.equalsIgnoreCase("V"))
            return Integer.valueOf(5);
        if (roman.equalsIgnoreCase("VI"))
            return Integer.valueOf(6);
        if (roman.equalsIgnoreCase("VII"))
            return Integer.valueOf(7);
        if (roman.equalsIgnoreCase("VIII"))
            return Integer.valueOf(8);
        if (roman.equalsIgnoreCase("IX"))
            return Integer.valueOf(9);
        if (roman.equalsIgnoreCase("X"))
            return Integer.valueOf(10);
        if (roman.equalsIgnoreCase("1"))
            return Integer.valueOf(1);
        if (roman.equalsIgnoreCase("2"))
            return Integer.valueOf(2);
        if (roman.equalsIgnoreCase("3"))
            return Integer.valueOf(3);
        if (roman.equalsIgnoreCase("4"))
            return Integer.valueOf(4);
        if (roman.equalsIgnoreCase("5"))
            return Integer.valueOf(5);
        if (roman.equalsIgnoreCase("6"))
            return Integer.valueOf(6);
        if (roman.equalsIgnoreCase("7"))
            return Integer.valueOf(7);
        if (roman.equalsIgnoreCase("8"))
            return Integer.valueOf(8);
        if (roman.equalsIgnoreCase("9"))
            return Integer.valueOf(9);
        if (roman.equalsIgnoreCase("10")) {
            return Integer.valueOf(10);
        }
        return Integer.valueOf(1);
    }

    @EventHandler
    public final void inventoryClick(InventoryClickEvent e) {
        boolean shift = false, numberkey = false;
        if (e.isCancelled())
            return;
        if (e.getAction() == InventoryAction.NOTHING)
            return;
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            shift = true;
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            numberkey = true;
        }
        if (e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER)
            return;
        if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER))
            return;
        if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER))
            return;
        if (!(e.getWhoClicked() instanceof Player))
            return;
        ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
            return;
        }

        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem());
            if (newArmorType != null) {
                boolean equipping = e.getRawSlot() != newArmorType.getSlot();
                if ((newArmorType.equals(ArmorType.HELMET) && equipping == isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) || (newArmorType.equals(ArmorType.CHESTPLATE) && equipping == isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) || (newArmorType.equals(ArmorType.LEGGINGS) && equipping == isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) || (newArmorType.equals(ArmorType.BOOTS) && equipping == isAirOrNull(e.getWhoClicked().getInventory().getBoots()))) {
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if (numberkey) {
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {


                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if (!isAirOrNull(hotbarItem)) {
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    } else {
                        newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
                    }

                }
            } else if (isAirOrNull(e.getCursor()) && !isAirOrNull(e.getCurrentItem())) {
                newArmorType = ArmorType.matchType(e.getCurrentItem());
            }


            if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
                if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey)
                    method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL)
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                Material mat = e.getClickedBlock().getType();
                for (String s : this.getBlocks()) {
                    if (mat.name().equalsIgnoreCase(s))
                        return;
                }
            }
            ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null && ((newArmorType
                    .equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet())) || (newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate())) || (newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings())) || (newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())))) {
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }


    @EventHandler
    public void inventoryDrag(InventoryDragEvent event) {
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty())
            return;
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(Integer.valueOf(0))) {
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)event.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (!isAirOrNull(i)) {
                Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        new BukkitRunnable() {
            public void run() {
                if (!(player.getKiller() instanceof Player)) {
                    return;
                }
                final Player killer = player.getKiller();
                for (String enchanter : Kits.getInstance().getCustomEnchantManager().getEnchants()) {
                    if (!enchanter.contains("Recover")) {
                        continue;
                    }
                    for (final ItemStack item : killer.getEquipment().getArmorContents()) {
                        if (Kits.getInstance().getCustomEnchantManager().hasEnchanters(item)) {
                            new BukkitRunnable() {
                                public void run() {
                                    killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 160, 2));
                                    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));

                                }
                            }.runTask(Kits.getInstance());
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Kits.getInstance());
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location from = event.getFrom();
        final Location to = event.getTo();
        new BukkitRunnable() {
            public void run() {
                if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {
                    for (String enchanter : Kits.getInstance().getCustomEnchantManager().getEnchants()) {
                        for (final ItemStack armor : player.getEquipment().getArmorContents()) {
                            if (Kits.getInstance().getCustomEnchantManager().hasEnchanters(armor)) {
                                if (enchanter.equalsIgnoreCase("Implants")) {
                                    if (player.getFoodLevel() < 20) {
                                        new BukkitRunnable() {
                                            public void run() {
                                                final int foodIncress = 1;
                                                if (player.getFoodLevel() + foodIncress <= 20) {
                                                    player.setFoodLevel(player.getFoodLevel() + foodIncress);
                                                }
                                                if (player.getFoodLevel() + foodIncress >= 20) {
                                                    player.setFoodLevel(20);
                                                }
                                            }
                                        }.runTask((Kits.getInstance()));
                                    }
                                }
                                if (enchanter.equalsIgnoreCase("Hell Forge")) {
                                    new BukkitRunnable() {
                                        public void run() {
                                            armor.setDurability((short) 0);
                                        }
                                    }.runTask(Kits.getInstance());
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Kits.getInstance());
    }


    private boolean isAirOrNull(ItemStack item) { return (item == null || item.getType().equals(Material.AIR)); }

    public boolean hasEnchanters(final ItemStack item) {
        if (item != null && item.getItemMeta() != null && item.hasItemMeta() && item.getItemMeta().getLore() != null && item.getItemMeta().hasLore()) {
            try {
                for (final String lore : item.getItemMeta().getLore()) {
                    for (String enchanter : this.getEnchants()) {
                        try {
                            final String[] split = lore.split(" ");
                            if (!lore.replace(" " , "_").equals(String.valueOf(enchanter))) {
                                continue;
                            }
                            return true;
                        }
                        catch (Exception ignored) {}
                    }
                }
            }
            catch (NullPointerException npe) {
                System.out.println("[mGkit Kit] Item:" + (item != null) + "Meta:" + (item.getItemMeta() != null) + "Lore" + (item.getItemMeta().getLore() != null));
                npe.printStackTrace();
            }
        }
        return false;
    }

    private List<String> getBlocks() {
        List<String> blocks = new ArrayList<>();
        blocks.add("DAYLIGHT_DETECTOR");
        blocks.add("DAYLIGHT_DETECTOR_INVERTED");
        blocks.add("FURNACE");
        blocks.add("CHEST");
        blocks.add("TRAPPED_CHEST");
        blocks.add("BEACON");
        blocks.add("DISPENSER");
        blocks.add("DROPPER");
        blocks.add("HOPPER");
        blocks.add("WORKBENCH");
        blocks.add("ENCHANTMENT_TABLE");
        blocks.add("ENDER_CHEST");
        blocks.add("ANVIL");
        blocks.add("BED_BLOCK");
        blocks.add("FENCE_GATE");
        blocks.add("SPRUCE_FENCE_GATE");
        blocks.add("BIRCH_FENCE_GATE");
        blocks.add("ACACIA_FENCE_GATE");
        blocks.add("JUNGLE_FENCE_GATE");
        blocks.add("DARK_OAK_FENCE_GATE");
        blocks.add("IRON_DOOR_BLOCK");
        blocks.add("WOODEN_DOOR");
        blocks.add("SPRUCE_DOOR");
        blocks.add("BIRCH_DOOR");
        blocks.add("JUNGLE_DOOR");
        blocks.add("ACACIA_DOOR");
        blocks.add("DARK_OAK_DOOR");
        blocks.add("WOOD_BUTTON");
        blocks.add("STONE_BUTTON");
        blocks.add("TRAP_DOOR");
        blocks.add("IRON_TRAPDOOR");
        blocks.add("DIODE_BLOCK_OFF");
        blocks.add("DIODE_BLOCK_ON");
        blocks.add("REDSTONE_COMPARATOR_OFF");
        blocks.add("REDSTONE_COMPARATOR_ON");
        blocks.add("FENCE");
        blocks.add("SPRUCE_FENCE");
        blocks.add("BIRCH_FENCE");
        blocks.add("JUNGLE_FENCE");
        blocks.add("DARK_OAK_FENCE");
        blocks.add("ACACIA_FENCE");
        blocks.add("NETHER_FENCE");
        blocks.add("BREWING_STAND");
        blocks.add("CAULDRON");
        blocks.add("SIGN_POST");
        blocks.add("WALL_SIGN");
        blocks.add("SIGN");
        blocks.add("DRAGON_EGG");
        blocks.add("LEVER");
        blocks.add("SHULKER_BOX");
        return blocks;
    }

}
