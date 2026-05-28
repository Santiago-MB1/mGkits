package me.santiago.kits.kit;

import me.santiago.kits.kit.main.ListenerManager;
import me.santiago.kits.kit.util.KitData;
import me.santiago.kits.Kits;
import me.santiago.kits.user.utils.Userdata;
import me.santiago.kits.util.config.ConfigFile;
import me.santiago.kits.util.Color;
import me.santiago.kits.util.ItemBuilder;
import me.santiago.kits.util.StaticUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.IntStream;

public class FreeManager implements Listener {

    private final ConfigFile file;

    private final List<KitData> kits;
    private final Map<UUID, String> editingKits;

    public FreeManager() {
        this.file = new ConfigFile("free.yml");

        this.kits = new ArrayList<>();
        this.editingKits = new HashMap<>();

        this.loadKits();

        Bukkit.getPluginManager().registerEvents(this, Kits.getInstance());
    }

    public void disable() {
        this.saveKits();

        this.kits.clear();
        this.editingKits.clear();
    }

    private void loadKits() {
        this.file.getKeys(false).forEach(kitName -> {
            ConfigurationSection section = this.file.getSection(kitName);

            KitData kit = new KitData();
            kit.setName(kitName);
            kit.setDelay(section.getInt("delay"));
            kit.setPermission(section.getString("permission"));
            kit.setColor(section.getString("color"));
            kit.setSlot(section.getInt("slot"));
            kit.setItem(section.getString("item"));
            kit.setDurability(section.getInt("durability"));
            kit.setContents(StaticUtils.itemStackArrayFromBase64(section.getString("contents")));
            kit.setArmor(StaticUtils.itemStackArrayFromBase64(section.getString("armor")));

            this.kits.add(kit);
        });
    }

    private void saveKits() {
        this.kits.forEach(kit -> {
            ConfigurationSection section = this.file.createSection(kit.getName());

            section.set("delay", kit.getDelay());
            section.set("color", kit.getColor());
            section.set("slot", kit.getSlot());
            section.set("item", kit.getItem());
            section.set("durability", kit.getDurability());
            section.set("permission", kit.getPermission());
            section.set("contents", StaticUtils.itemStackArrayToBase64(kit.getContents()));
            section.set("armor", StaticUtils.itemStackArrayToBase64(kit.getArmor()));
        });

        this.file.save();
    }

    public KitData getKit(String name) {
        return this.kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<String> getKits() {
        return new ArrayList<>(this.file.getKeys(false));
    }

    public void createKit(String kitName) {
        KitData kit = new KitData();
        kit.setName(kitName);
        kit.setDelay(86400);
        kit.setPermission("kit.permission." + kitName);
        kit.setColor("&4&l");
        kit.setSlot(0);
        kit.setItem("DIAMOND_CHESTPLATE");
        kit.setDurability(0);
        kit.setContents(new ItemStack[36]);
        kit.setArmor(new ItemStack[4]);

        this.kits.add(kit);
    }

    public void removeKit(KitData kit) {
        this.kits.remove(kit);

        this.file.set(kit.getName(), null);
        this.file.save();
    }

    private boolean hasKitPermission(Player player, KitData kit) {
        if (player.isOp()) return true;

        return player.hasPermission(kit.getPermission()) || player.hasPermission("kit.permission.*");
    }

    private boolean isEditingKit(Player player) {
        return this.editingKits.containsKey(player.getUniqueId());
    }

    private String getCooldownString(Userdata data, KitData kit) {
        return DurationFormatUtils.formatDurationWords(data.getNormalKitDelays().get(kit.getName()) - System.currentTimeMillis(), true, true);
    }

    private void applyKitCooldown(Userdata data, KitData kit) {
        data.getNormalKitDelays().put(kit.getName(), kit.getDelay() == -1 ? -1 : System.currentTimeMillis() + (kit.getDelay() * 1000L));
    }

    public void editKit(Player player, KitData kit) {
        this.editingKits.put(player.getUniqueId(), kit.getName());

        Inventory inventory = Bukkit.createInventory(null, 54, Color.translate("&4Kit: &c" + kit.getName()));
        inventory.setContents(kit.getContents());
        IntStream.rangeClosed(45, 48).forEach(i -> inventory.setItem(i, kit.getArmor()[i - 45]));

        ItemStack placeholder = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7).setName(ChatColor.RED + "Kit Editor").build();
        IntStream.rangeClosed(36, 44).forEach(i -> inventory.setItem(i, placeholder));
        IntStream.rangeClosed(49, 51).forEach(i -> inventory.setItem(i, placeholder));

        inventory.setItem(52, new ItemBuilder(Material.STAINED_CLAY, 1, 14).setName(ChatColor.RED + "Close Editor").build());
        inventory.setItem(53, new ItemBuilder(Material.STAINED_CLAY, 1, 5).setName(ChatColor.GREEN + "Save Kit").build());

        player.openInventory(inventory);
    }

    public void editPreview(Player player, KitData kit) {
        Inventory inventory = Bukkit.createInventory(null, 54, Color.translate("&4Kit Preview"));
        inventory.setContents(kit.getContents());
        IntStream.rangeClosed(45,48).forEach(i -> inventory.setItem(i, kit.getArmor()[i - 45]));

        ItemStack placeholder = new ItemBuilder(Material.AIR).build();
        IntStream.rangeClosed(36, 44).forEach(i -> inventory.setItem(i, placeholder));
        IntStream.rangeClosed(49, 51).forEach(i -> inventory.setItem(i, placeholder));

        inventory.setItem(53, new ItemBuilder(Material.PAPER).setName(ChatColor.RED + "Close Preview").build());

        player.openInventory(inventory);
    }


    private boolean isOnCooldown(Player player, KitData kit) {
        if (kit.getDelay() == 0) return false;
        if (player.hasPermission("kit.permission.delay.bypass")) return false;

        Userdata data = Kits.getInstance().getUserdataManager().getUserdata(player);
        if (!data.getNormalKitDelays().containsKey(kit.getName())) return false;

        long delay = data.getNormalKitDelays().get(kit.getName());
        return delay == -1 || System.currentTimeMillis() < delay;
    }

    public void listKits(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.translate("&b&lmGkits &7┃ &cOnly players"));
            return;
        }

        Player player = (Player) sender;
        StringJoiner availableKits = new StringJoiner(Color.translate("&7, "));

        this.kits.stream().sorted(Comparator.comparing(KitData::getName)).forEach(kit -> {
            if (!this.hasKitPermission(player, kit)) return;

            if (this.isOnCooldown(player, kit)) {
                availableKits.add(Color.translate("&c" + kit.getName()));
                return;
            }

            availableKits.add(Color.translate("&a" + kit.getName()));
        });

        if (availableKits.length() == 0) {
            sender.sendMessage(Color.translate("&b&lmGkits &7┃ &cThere is no registration of kits."));
            return;
        }

        sender.sendMessage(Color.translate("&b&lmGkits &7┃ &eKits you can use: <kits>").replace("<kits>", availableKits.toString()));
    }

    public void giveKitWithCommand(CommandSender sender, Player player, KitData kit) {
        kit.applyKit(player);

        player.updateInventory();
        player.sendMessage(Color.translate("&b&lmGkits &7┃&a You have just received the kit <kit> by a manager.")
                .replace("<kit>", kit.getName()));

        sender.sendMessage(Color.translate("&b&lmGkits &f(ADMIN) &7┃ &aYou just gave the kit <kit> to the user <target>")
                .replace("<kit>", kit.getName())
                .replace("<target>", player.getName()));
    }

    @EventHandler(ignoreCancelled = true)
    public void  onInteractivePreview(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!event.getClickedInventory().getName().startsWith(Color.translate("&4Kit Preview"))) return;
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        event.setCancelled(true);

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Close Preview")) {
            this.getOpenInventory(player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (!this.isEditingKit(player) || event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        KitData kit = this.getKit(this.editingKits.get(player.getUniqueId()));
        if (kit == null) return;

        if (kit.shouldCancelEvent(event.getSlot())) {
            event.setCancelled(true);
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        if (item.getItemMeta().getDisplayName().contains("Close Editor")) {
            StaticUtils.sync(player::closeInventory);
            return;
        }

        if (item.getItemMeta().getDisplayName().contains("Save Kit")) {
            ItemStack[] contents = new ItemStack[36];
            System.arraycopy(event.getInventory().getContents(), 0, contents, 0, contents.length);
            kit.setContents(StaticUtils.getRealItems(contents));


            ItemStack[] armor = kit.getArmor();
            IntStream.rangeClosed(45, 48).forEach(i -> armor[i - 45] = event.getInventory().getItem(i));

            this.editingKits.remove(player.getUniqueId());
            player.sendMessage(Color.translate("&b&lmGkits &7┃ &aKit &f<kit> &ahas been edited.").replace("<kit>", kit.getName()));

            StaticUtils.sync(player::closeInventory);
        }
    }

    @EventHandler
    public void onClickedInventory(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!event.getClickedInventory().getName().startsWith(Color.translate("&4&lKit Selector"))) return;
        event.setCancelled(true);

        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Back Menu")) {
            player.closeInventory();
            ListenerManager.getOpenInventory(player);
            return;
        }

        this.kits.forEach(kit ->{
            if (event.getSlot() == kit.getSlot()) {
                if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    event.setCancelled(true);
                    player.closeInventory();
                    this.editPreview(player, kit);
                } else if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    event.setCancelled(true);
                     if (this.isOnCooldown(player, kit)) {
                        Userdata data = Kits.getInstance().getUserdataManager().getUserdata(player);
                        player.sendMessage(kit.getDelay() == -1
                                ? Color.translate("&b&lmGkits &7┃&c You can only use the <kit> once.")
                                .replace("<kit>", kit.getName())
                                : Color.translate("&b&lmGkits &7┃&c You cannot use the kit <kit>, as you have a cooldown of <time>.")
                                .replace("<kit>", kit.getName()).replace("<time>", this.getCooldownString(data, kit)));
                        player.closeInventory();
                        return;
                     }
                     if (!player.hasPermission("kit.permission." + kit.getName())) {
                             player.sendMessage(Color.translate("&b&lmGkits &7┃&c You currently do not have permission to use the <kit> kit, please purchase it from store.example.net").replace("<kit>", kit.getName()));
                     } else {
                        Userdata data = Kits.getInstance().getUserdataManager().getUserdata(player);
                        this.applyKitCooldown(data, kit);
                        kit.applyKit(player);
                        player.updateInventory();
                        player.sendMessage(Color.translate("&b&lmGkits &7┃&a You just received the kit <kit> successfully.").replace("<kit>", kit.getName()));
                     }
                    player.closeInventory();
                }
            }
        });
    }

    public void getOpenInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (6 * 9), Color.translate("&4&lKit Selector"));

        this.kits.forEach(kit -> {

            ItemStack itemStack = new ItemBuilder(Material.valueOf(kit.getItem()),1 , kit.getDurability())
                    .setName(Color.translate("&7Kit " + this.getStatusKitName(player, kit) + kit.getName().replace("_", " ")))
                    .setLore(
                            Color.translate("&7Cooldown: &f<time>").replace("<time>", this.getValue(kit) != -1 ? this.getValue(kit) + "d" : "1 used"),
                            Color.translate(" "),
                            Color.translate("&7Available in: <status>").replace("<status>", this.getStatus(player, kit)),
                            Color.translate("&7Store: &fstore.example.net"),
                            Color.translate(""),
                            Color.translate("&7&l(&c&l!&7&l) &7Right click to preview"))
                    .build();

            Material material = Material.STAINED_GLASS_PANE;

            int durability = Kits.getInstance().getConfig().getInt("SELECTOR.REFILL_DATA");
            ItemStack cristal = new ItemBuilder(material).setDurability(durability).build();

            ItemStack leave_kit = new ItemBuilder(Material.FEATHER).setName(Color.translate("&cBack Menu")).build();

            inventory.setItem(kit.getSlot(), itemStack); // set primary gkit items
            inventory.setItem(53, leave_kit);

            for (int i = 0; i < inventory.getSize(); ++i) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(kit.getSlot(), itemStack); // set gkit items
                    inventory.setItem(i, cristal); // set panel cristal
                }
            }

        });

        player.openInventory(inventory);
    }

    public String getStatusKitName(Player player, KitData kit) {
        return (player.hasPermission("kit.permission.*") || player.hasPermission("kit.permission." + kit.getName())) ? (this.isOnCooldown(player, kit) ? "&e" : kit.getColor()) : "&c";
    }

    public String getStatus(Player player, KitData kit) {
        return (player.hasPermission("kit.permission.*") || player.hasPermission("kit.permission." + kit.getName())) ? (this.isOnCooldown(player, kit) ? Color.translate("&cIn Cooldown") : Color.translate("&aNow!")) : Color.translate("&cNo permission");
    }

    public Integer getValue(KitData kit) {
        int value = kit.getDelay();

        return ((value / 60) / 60) / 24;
    }
}
