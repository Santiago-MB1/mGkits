package me.santiago.kits;

import me.santiago.kits.commands.*;
import me.santiago.kits.customenchants.CustomEnchantManager;
import me.santiago.kits.customenchants.EnchanterBookManager;
import me.santiago.kits.kit.FreeManager;
import me.santiago.kits.kit.SpecialManager;
import me.santiago.kits.kit.VipManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.santiago.kits.user.UserdataManager;
import me.santiago.kits.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Modifier;

public class Kits extends JavaPlugin {

    private static Kits instance;
    private Gson gson;
    private UserdataManager userdataManager;
    private FreeManager FreeManager;
    private SpecialManager specialManager;
    private VipManager vipManager;
    private CustomEnchantManager enchantsManager;
    private EnchanterBookManager enchanterBookManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.saveConfig();
        this.log("&3[]&f=============================================&f&3[]");
        this.log("- &bName&7: &cmGkits");
        this.log("- &bVersion&7: &f0.0.1-STABLED");
        this.log("- &bAuthor&7: &bSantiago_cr");
        this.log("- &bInformation:");
        this.log("  &b* &fRegister Gson...");
        this.registerGson();
        this.log("  &b* &fRegister Player Data...");
        this.registerPlayerData();
        this.log("  &b* &fRegister System Kits Data...");
        this.registerKitsData();
        this.log("  &b* &fRegister Custom Enchants...");
        this.registerCustomEnchant();
        this.log("  &b* &fRegister Commands...");
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.log("&3[]&f=============================================&f&3[]");
        this.log("- &bInformation:");
        this.log("  &b* &fSave Player Data...");
        userdataManager.disable();
        this.log("  &b* &fSave Kits Data...");
        FreeManager.disable();
        vipManager.disable();
        specialManager.disable();
        this.log("&3[]&f=============================================&f&3[]");
    }

    public void registerGson() {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
                .enableComplexMapKeySerialization().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                .create();
    }

    public void registerPlayerData() {
        this.userdataManager = new UserdataManager();
    }

    public void registerKitsData() {
        this.vipManager = new VipManager();
        this.specialManager = new SpecialManager();
        this.FreeManager = new FreeManager();
    }

    public void registerCustomEnchant() {
        this.enchantsManager = new CustomEnchantManager();
        this.enchanterBookManager = new EnchanterBookManager();
    }

    public void registerCommands() {
        this.getCommand("gkit").setExecutor(new KitCommand());
        this.getCommand("akit").setExecutor(new KitAdminCommand());
        this.getCommand("customenchant").setExecutor(new AddEnchantCommand());
        this.getCommand("bookenchant").setExecutor(new BookEnchantCommand());
        this.getCommand("ce").setExecutor(new CustomShopCommand());
        this.log("&3[]&f=============================================&f&3[]");
    }

    public void log(String message) { Bukkit.getConsoleSender().sendMessage(Color.translate(message)); }
    public static Kits getInstance() { return instance; }
    public Gson getGson() { return gson;}
    public UserdataManager getUserdataManager() { return userdataManager;}
    public FreeManager getKitManager() { return FreeManager;}
    public SpecialManager getSpecialManager() { return specialManager;}
    public VipManager getVipManager() { return vipManager;}
    public CustomEnchantManager getCustomEnchantManager() { return enchantsManager;}
    public EnchanterBookManager getEnchanterBook() {return enchanterBookManager;}

}
