package com.github.ipecter.rtu.lib.util.support;

import com.github.ipecter.rtu.lib.RTULib;
import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemUtil {

    @Nullable
    public static ItemStack fromId(@NotNull String namespacedID) {
        String[] split = namespacedID.split(":");
        String platform = split[0].toLowerCase();
        switch (platform) {
            case "oraxen" -> {
                if (RTULib.getInstance().isEnabledDependency("Oraxen")) {
                    ItemBuilder itemBuilder = OraxenItems.getItemById(split[1]);
                    return itemBuilder != null ? itemBuilder.build() : null;
                } else return null;
            }
            case "itemsadder" -> {
                if (RTULib.getInstance().isEnabledDependency("ItemsAdder")) {
                    CustomStack customStack = CustomStack.getInstance(split[1] + ":" + split[2]);
                    return customStack != null ? customStack.getItemStack() : null;
                } else return null;
            }
            default -> {
                Material material = Material.getMaterial(split[1].toUpperCase());
                return material != null ? new ItemStack(material) : null;
            }
        }
    }

    public static String fromItemStack(@NotNull ItemStack itemStack) {
        if (RTULib.getInstance().isEnabledDependency("Oraxen")) {
            String oraxen = OraxenItems.getIdByItem(itemStack);
            if (oraxen != null) return "oraxen:" + oraxen;
        }
        if (RTULib.getInstance().isEnabledDependency("ItemsAdder")) {
            CustomStack itemsAdder = CustomStack.byItemStack(itemStack);
            if (itemsAdder != null) return "itemsadder:" + itemsAdder.getNamespacedID();
        }
        return "minecraft:" + itemStack.getType().toString().toLowerCase();
    }
}
