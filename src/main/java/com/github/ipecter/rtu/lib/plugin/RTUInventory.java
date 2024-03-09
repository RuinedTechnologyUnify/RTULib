package com.github.ipecter.rtu.lib.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class RTUInventory implements InventoryHolder, Listener {

    public void onClick(Event event, Click click) {
    }

    public void onClose(Event event) {
    }

    public record Event(Inventory inventory, Player player, boolean isInventory) {
    }

    public record Click(int slot, InventoryType.SlotType slotType, ClickType type) {
    }

}
