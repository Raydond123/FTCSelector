package me.raydond123.ftcselector;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class PlayerListener implements Listener {

    FTCSelector plugin;

    public PlayerListener(FTCSelector plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Inventory inventory = e.getInventory();
        Player player = (Player) e.getWhoClicked();

        if(inventory.equals(plugin.inv)) {

            e.setCancelled(true);

            ItemStack clicked = e.getCurrentItem();

            if(plugin.data.get(clicked.getType()) != null) {

                String serverName = plugin.data.get(clicked.getType());

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);

                try {

                    out.writeUTF("Connect");
                    out.writeUTF(serverName);

                } catch (Exception exception) {

                    exception.printStackTrace();

                }

                player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());

                player.closeInventory();

            } else if(plugin.data2.get(clicked.getType()) != null) {

                String messageRaw = plugin.data2.get(clicked.getType());
                String[] message = messageRaw.split("~");

                for(int i = 0; i < message.length; i++) {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message[i]));

                }

                player.closeInventory();

            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        ItemStack held = player.getItemInHand();

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(held.getType().equals(Material.COMPASS)) {

                player.openInventory(plugin.inv);

            }

        }

    }

}
