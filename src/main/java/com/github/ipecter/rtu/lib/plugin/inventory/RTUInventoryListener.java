package com.github.ipecter.rtu.lib.plugin.inventory;

import com.github.ipecter.rtu.lib.plugin.RTUInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class RTUInventoryListener implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTopInventory().getHolder() instanceof RTUInventory rsInventory) {
            boolean isPlayerInventory = inv != null && !(inv.getHolder() instanceof RTUInventory);
            RTUInventory.Event event = new RTUInventory.Event(inv, player, isPlayerInventory);
            RTUInventory.Click click = new RTUInventory.Click(e.getSlot(), e.getSlotType(), e.getClick());
            rsInventory.onClick(event, click);
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getPlayer();
        if (inv.getHolder() instanceof RTUInventory rsInventory) {
            boolean isPlayerInventory = !(inv.getHolder() instanceof RTUInventory);
            RTUInventory.Event event = new RTUInventory.Event(inv, player, isPlayerInventory);
            rsInventory.onClose(event);
        }
    }

}
