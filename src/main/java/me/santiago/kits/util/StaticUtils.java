package me.santiago.kits.util;

import me.santiago.kits.Kits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class StaticUtils {

    private static Random random;

    static {
        StaticUtils.random = new Random();
    }

    public static void addToInventoryOrDropToFloor(Player player, ItemStack itemStack) {
        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    public static boolean isInvFull(final Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public static boolean randomPicker(final int min, final int max) {
        if (max <= min || max <= 0) {
            return true;
        }
        final int chance = 1 + StaticUtils.random.nextInt(max);
        return chance >= 1 && chance <= min;
    }

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        if(items.length == 0) {
            return "";
        }

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeInt(items.length);

            for(ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save items.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) {
        if(data == null || data.isEmpty()) {
            return new ItemStack[0];
        }

        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for(int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            return items;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to load items.", e);
        }
    }

    public static void sync(Callable callable) {
        Bukkit.getScheduler().runTask(Kits.getInstance(), callable::call);
    }

    public static void async(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(Kits.getInstance(), callable::call);
    }

    public interface Callable {
        void call();
    }


    public static ItemStack[] getRealItems(ItemStack[] items) {
        return Stream.of(items).filter(item -> item != null && item.getType() != Material.AIR).toArray(ItemStack[]::new);
    }


    public static Long tryParseLong(String value) {
        try {
            return Long.valueOf(value);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    public static Integer tryParseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public static int parseSeconds(String value) {
        if(isInteger(value)) return Math.abs(Integer.parseInt(value));
        if(value.equalsIgnoreCase("0s")) return 0;

        value = value.toLowerCase();
        int seconds = 0;

        for(TimeFormat format : TimeFormat.values()) {
            if(!value.contains(format.getTimeChar())) continue;

            String[] split = value.split(format.getTimeChar());
            if(!isInteger(split[0])) continue;

            seconds += Math.abs(Integer.parseInt(split[0])) * format.getSeconds();
            if(split.length > 1) value = split[1];
        }

        return seconds == 0 ? -1 : seconds;
    }


    @Getter
    @AllArgsConstructor
    public enum TimeFormat {

        DAY("d", TimeUnit.DAYS.toSeconds(1L)),
        HOUR("h", TimeUnit.HOURS.toSeconds(1L)),
        MINUTE("m", TimeUnit.MINUTES.toSeconds(1L)),
        SECOND("s", 1L);

        private final String timeChar;
        private final long seconds;
    }

    public static String convertLevelString(final int i) {
        switch (i) {
            case 0:
            case 1: {
                return "I";
            }
            case 2: {
                return "II";
            }
            case 3: {
                return "III";
            }
            case 4: {
                return "IV";
            }
            case 5: {
                return "V";
            }
            case 6: {
                return "VI";
            }
            case 7: {
                return "VII";
            }
            case 8: {
                return "VIII";
            }
            case 9: {
                return "IX";
            }
            case 10: {
                return "X";
            }
            default: {
                return String.valueOf(i);
            }
        }
    }

    public static int convertLevelInteger(final String i) {
        switch (i) {
            case "I": {
                return 1;
            }
            case "II": {
                return 2;
            }
            case "III": {
                return 3;
            }
            case "IV": {
                return 4;
            }
            case "V": {
                return 5;
            }
            case "VI": {
                return 6;
            }
            case "VII": {
                return 7;
            }
            case "VIII": {
                return 8;
            }
            case "IX": {
                return 9;
            }
            case "X": {
                return 10;
            }
            default: {
                return 0;
            }
        }
    }

}
