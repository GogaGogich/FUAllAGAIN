package com.example.laboratory.listeners;

import com.example.laboratory.LaboratoryPlugin;
import com.nexomc.nexo.api.NexoItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractListener implements Listener {
    
    private final LaboratoryPlugin plugin;
    
    public ItemInteractListener(LaboratoryPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        if (item == null) {
            return;
        }
        
        String itemId = NexoItems.idFromItem(item);
        if (itemId == null) {
            return;
        }
        
        switch (itemId) {
            case "geiger_counter":
                handleGeigerCounter(player);
                event.setCancelled(true);
                break;
            case "uranium_capsule":
                handleUraniumCapsule(player, item);
                event.setCancelled(true);
                break;
            case "tablet":
                handleTablet(player, event);
                event.setCancelled(true);
                break;
        }
    }
    
    private void handleGeigerCounter(Player player) {
        int radiation = plugin.getRadiationManager().getPlayerRadiation(player);
        player.sendMessage("§e[Счётчик Гейгера] §fУровень радиации: §c" + radiation + " §fрад/с");
        
        if (radiation == 0) {
            player.sendMessage("§a[Счётчик Гейгера] Радиационный фон в норме");
        } else if (radiation < 20) {
            player.sendMessage("§e[Счётчик Гейгера] Низкий уровень радиации");
        } else if (radiation < 50) {
            player.sendMessage("§6[Счётчик Гейгера] Средний уровень радиации");
        } else if (radiation < 100) {
            player.sendMessage("§c[Счётчик Гейгера] Высокий уровень радиации!");
        } else {
            player.sendMessage("§4[Счётчик Гейгера] КРИТИЧЕСКИЙ уровень радиации!");
        }
    }
    
    private void handleUraniumCapsule(Player player, ItemStack capsule) {
        // TODO: Implement uranium capsule functionality
        player.sendMessage("§eФункциональность урановой капсулы будет добавлена позже");
    }
    
    private void handleTablet(Player player, PlayerInteractEvent event) {
        // TODO: Implement tablet functionality
        player.sendMessage("§eФункциональность планшета будет добавлена позже");
    }
}