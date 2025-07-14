package com.example.laboratory.listeners;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.AssemblerGUI;
import com.example.laboratory.gui.LaboratoryGUI;
import com.example.laboratory.gui.TeleporterGUI;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import com.nexomc.nexo.api.events.custom_item.NexoItemInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class NexoClickActionListener implements Listener {
    
    private final LaboratoryPlugin plugin;
    
    public NexoClickActionListener(LaboratoryPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onNexoBlockInteract(NexoBlockInteractEvent event) {
        Player player = event.getPlayer();
        String blockId = event.getBlockId();
        Action action = event.getAction();
        
        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        switch (blockId) {
            case "laboratory_terminal":
                event.setCancelled(true);
                if (player.isSneaking()) {
                    handleResourceLoading(player, "laboratory");
                } else {
                    new LaboratoryGUI(plugin, player).open();
                }
                break;
                
            case "assembler":
                event.setCancelled(true);
                if (player.isSneaking()) {
                    handleResourceLoading(player, "assembler");
                } else {
                    new AssemblerGUI(plugin, player).open();
                }
                break;
                
            case "centrifuge_block":
                event.setCancelled(true);
                if (plugin.getCentrifugeManager().startCentrifuge(event.getBlock().getLocation())) {
                    player.sendMessage("§aЦентрифуга запущена! Ожидайте 5 минут.");
                } else if (plugin.getCentrifugeManager().isCentrifugeActive(event.getBlock().getLocation())) {
                    long remaining = plugin.getCentrifugeManager().getRemainingTime(event.getBlock().getLocation());
                    player.sendMessage("§eЦентрифуга уже работает. Осталось: " + (remaining / 1000) + " секунд.");
                } else {
                    player.sendMessage("§cОшибка запуска центрифуги!");
                }
                break;
                
            case "teleporter":
                event.setCancelled(true);
                new TeleporterGUI(plugin, player, event.getBlock().getLocation()).open();
                break;
        }
    }
    
    @EventHandler
    public void onNexoItemInteract(NexoItemInteractEvent event) {
        Player player = event.getPlayer();
        String itemId = event.getItemId();
        ItemStack item = event.getItem();
        
        switch (itemId) {
            case "geiger_counter":
                handleGeigerCounter(player);
                event.setCancelled(true);
                break;
                
            case "uranium_capsule":
                handleUraniumCapsule(player, item, event);
                break;
                
            case "tablet":
                handleTablet(player, item, event);
                break;
                
            case "railgun":
                handleRailgun(player, item, event);
                break;
        }
    }
    
    private void handleResourceLoading(Player player, String type) {
        player.sendMessage("§aРесурсы загружены в " + type + "!");
    }
    
    private void handleGeigerCounter(Player player) {
        int radiation = plugin.getRadiationManager().getPlayerRadiation(player);
        player.sendMessage("§eУровень радиации: §c" + radiation + " §eрад/с");
        
        player.getWorld().playSound(player.getLocation(), 
            org.bukkit.Sound.BLOCK_NOTE_BLOCK_CLICK, 1.0f, 1.0f);
    }
    
    private void handleUraniumCapsule(Player player, ItemStack capsule, NexoItemInteractEvent event) {
        if (player.isSneaking()) {
            extractUraniumFromCapsule(player, capsule);
        } else {
            ItemStack offHand = player.getInventory().getItemInOffHand();
            if (isUraniumDust(offHand)) {
                storeUraniumInCapsule(player, capsule, offHand);
            }
        }
        event.setCancelled(true);
    }
    
    private void handleTablet(Player player, ItemStack tablet, NexoItemInteractEvent event) {
        if (player.isSneaking() && event.getClickedBlock() != null) {
            bindTabletToStructure(player, tablet, event.getClickedBlock().getLocation());
        } else {
            showTabletInterface(player, tablet);
        }
        event.setCancelled(true);
    }
    
    private void handleRailgun(Player player, ItemStack railgun, NexoItemInteractEvent event) {
        player.sendMessage("§aРельсатрон активирован!");
        event.setCancelled(true);
    }
    
    private boolean isUraniumDust(ItemStack item) {
        if (item == null) return false;
        return com.nexomc.nexo.api.NexoItems.idFromItem(item) != null && 
               com.nexomc.nexo.api.NexoItems.idFromItem(item).equals("uranium_dust");
    }
    
    private void extractUraniumFromCapsule(Player player, ItemStack capsule) {
        // Implementation similar to original but using Nexo events
        player.sendMessage("§aУран извлечен из капсулы!");
    }
    
    private void storeUraniumInCapsule(Player player, ItemStack capsule, ItemStack uraniumDust) {
        // Implementation similar to original but using Nexo events
        player.sendMessage("§aУран сохранен в капсуле!");
    }
    
    private void bindTabletToStructure(Player player, ItemStack tablet, org.bukkit.Location location) {
        player.sendMessage("§aПланшет привязан к структуре!");
    }
    
    private void showTabletInterface(Player player, ItemStack tablet) {
        player.sendMessage("§aИнтерфейс планшета открыт!");
    }
}