package me.raydond123.ftcselector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class FTCSelector extends JavaPlugin implements Listener {

    Logger logger = Logger.getLogger("Minecraft");
    public Inventory inv;
    PlayerListener playerListener;

    public void onEnable() {

        saveDefaultConfig();
        saveConfig();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        setupInv();

        playerListener = new PlayerListener(this);

        Bukkit.getPluginManager().registerEvents(playerListener, this);

        logger.info("[FTCSelector] The plugin has been enabled!");

    }

    public void onDisable() {

        playerListener = null;
        inv = null;
        data = null;

        logger.info("[FTCSelector] The plugin has been disabled!");

    }

    HashMap<Material, String> data = new HashMap<Material, String>();
    HashMap<Material, String> data2 = new HashMap<Material, String>();

    public void setupInv() {

        inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "" + ChatColor.BOLD + "FTC Selector");

        for(String raw : getConfig().getStringList("server-items")) {

            String[] data = raw.split(":");
            // - [Server Name]:[Material ID]:[Material Name]:[Slot #]

            String materialIdRaw = data[1];
            int materialId = Integer.valueOf(materialIdRaw);
            String materialName = data[2];
            ItemStack material = new ItemStack(Material.getMaterial(materialId));
            ItemMeta materialMeta = material.getItemMeta();
            materialMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', materialName));
            material.setItemMeta(materialMeta);

            String numberRaw = data[3];
            int number = Integer.valueOf(numberRaw);
            inv.setItem(number, material);

            String serverName = data[0];

            this.data.put(Material.getMaterial(materialId), serverName);

        }

        for(String raw : getConfig().getStringList("message-items")) {

            String[] data = raw.split(":");
            // - [Message]:[Material ID]:[Material Name]:[Slot #]

            String rawMessageData = data[0];
            String[] messageData = rawMessageData.split("|");
            Bukkit.broadcastMessage(rawMessageData);

            String materialIdRaw = data[1];
            int materialId = Integer.valueOf(materialIdRaw);
            String materialName = data[2];
            ItemStack material = new ItemStack(Material.getMaterial(materialId));
            ItemMeta materialMeta = material.getItemMeta();
            materialMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', materialName));
            material.setItemMeta(materialMeta);

            String numberRaw = data[3];
            int number = Integer.valueOf(numberRaw);
            inv.setItem(number, material);

            this.data2.put(Material.getMaterial(materialId),rawMessageData);

        }

        for(String raw : getConfig().getStringList("empty-items")) {

            String[] data = raw.split(":");
            // - [Material ID]:[Material Name]:[Slot #]

            String materialIdRaw = data[0];
            int materialId = Integer.valueOf(materialIdRaw);
            String materialName = data[1];
            ItemStack material = new ItemStack(Material.getMaterial(materialId));
            ItemMeta materialMeta = material.getItemMeta();
            materialMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', materialName));
            material.setItemMeta(materialMeta);

            String numberRaw = data[2];
            int number = Integer.valueOf(numberRaw);
            inv.setItem(number, material);

        }

    }

}
