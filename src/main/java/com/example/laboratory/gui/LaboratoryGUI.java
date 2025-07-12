package com.example.laboratory.gui;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.models.Research;
import com.nexomc.nexo.api.NexoItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaboratoryGUI implements Listener {
    
    private final LaboratoryPlugin plugin;
    private final Player player;
    private final Inventory inventory;
    
    public LaboratoryGUI(LaboratoryPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 54, "§8Терминал лаборатории");
        setupGUI();
    }
    
    private void setupGUI() {
        Map<String, Research> researches = plugin.getResearchManager().getAllResearches();
        int slot = 0;
        
        for (Research research : researches.values()) {
            if (slot >= 45) break; // Leave space for navigation
            
            ItemStack item = createResearchItem(research);
            inventory.setItem(slot, item);
            slot++;
        }
        
        // Add active research display
        Research activeResearch = plugin.getResearchManager().getActiveResearch(player);
        if (activeResearch != null) {
            ItemStack activeItem = createActiveResearchItem(activeResearch);
            inventory.setItem(49, activeItem);
        }
        
        // Add close button
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeButton.getItemMeta();
        closeMeta.setDisplayName("§cЗакрыть");
        closeButton.setItemMeta(closeMeta);
        inventory.setItem(53, closeButton);
    }
    
    private ItemStack createResearchItem(Research research) {
        boolean canResearch = plugin.getResearchManager().canResearch(player, research.getId());
        boolean hasResearched = plugin.getResearchManager().hasResearched(player, research.getId());
        
        ItemStack item;
        if (hasResearched) {
            item = new ItemStack(Material.GREEN_CONCRETE);
        } else if (canResearch) {
            item = new ItemStack(Material.YELLOW_CONCRETE);
        } else {
            item = new ItemStack(Material.RED_CONCRETE);
        }
        
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§f" + research.getName());
        
        List<String> lore = new ArrayList<>();
        lore.add("§7ID: §e" + research.getId());
        lore.add("§7Время: §e" + formatTime(research.getTimeSeconds()));
        lore.add("");
        lore.add("§7Материалы:");
        
        for (ItemStack material : research.getMaterials()) {
            String materialName = material.getType().name();
            String nexoId = NexoItems.idFromItem(material);
            if (nexoId != null) {
                materialName = nexoId;
            }
            lore.add("§8- §f" + material.getAmount() + "x " + materialName);
        }
        
        if (!research.getPrerequisites().isEmpty()) {
            lore.add("");
            lore.add("§7Требования:");
            for (String prereq : research.getPrerequisites()) {
                boolean hasPrereq = plugin.getResearchManager().hasResearched(player, prereq);
                lore.add("§8- " + (hasPrereq ? "§a✓" : "§c✗") + " §f" + prereq);
            }
        }
        
        lore.add("");
        if (hasResearched) {
            lore.add("§a✓ Исследовано");
        } else if (canResearch) {
            lore.add("§eНажмите для исследования");
        } else {
            lore.add("§cНедоступно");
        }
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    
    private ItemStack createActiveResearchItem(Research research) {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Активное исследование");
        
        List<String> lore = new ArrayList<>();
        lore.add("§f" + research.getName());
        
        long remaining = plugin.getResearchManager().getRemainingTime(player);
        if (remaining > 0) {
            lore.add("§7Осталось: §e" + formatTime((int) remaining));
        } else {
            lore.add("§a✓ Готово к сбору!");
        }
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }
    
    public void open() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        player.openInventory(inventory);
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) {
            return;
        }
        
        event.setCancelled(true);
        
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        
        Player clicker = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        
        if (clicked == null || clicked.getType() == Material.AIR) {
            return;
        }
        
        if (clicked.getType() == Material.BARRIER) {
            clicker.closeInventory();
            return;
        }
        
        if (clicked.getType() == Material.YELLOW_CONCRETE) {
            // Start research
            ItemMeta meta = clicked.getItemMeta();
            if (meta != null && meta.hasLore()) {
                List<String> lore = meta.getLore();
                for (String line : lore) {
                    if (line.startsWith("§7ID: §e")) {
                        String researchId = line.substring(8);
                        plugin.getResearchManager().startResearch(clicker, researchId);
                        clicker.sendMessage("§aИсследование '" + researchId + "' начато!");
                        clicker.closeInventory();
                        return;
                    }
                }
            }
        }
        
        if (clicked.getType() == Material.CLOCK) {
            // Check if research is complete
            if (plugin.getResearchManager().isResearchComplete(clicker)) {
                plugin.getResearchManager().completeResearch(clicker);
                clicker.sendMessage("§aИсследование завершено! Соберите результат в сборщике.");
                clicker.closeInventory();
            } else {
                clicker.sendMessage("§eИсследование еще не завершено.");
            }
        }
    }
    
    public void close() {
        HandlerList.unregisterAll(this);
    }
}