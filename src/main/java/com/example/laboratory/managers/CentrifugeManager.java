package com.example.laboratory.managers;

import com.example.laboratory.LaboratoryPlugin;
import com.nexomc.nexo.api.NexoItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CentrifugeManager {
    
    private final LaboratoryPlugin plugin;
    private final Map<Location, Long> activeCentrifuges;
    private final Random random;
    
    public CentrifugeManager(LaboratoryPlugin plugin) {
        this.plugin = plugin;
        this.activeCentrifuges = new HashMap<>();
        this.random = new Random();
        startCentrifugeTask();
    }
    
    private void startCentrifugeTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                activeCentrifuges.entrySet().removeIf(entry -> {
                    if (currentTime - entry.getValue() >= 300000) { // 5 minutes
                        completeCentrifuge(entry.getKey());
                        return true;
                    }
                    return false;
                });
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
    
    public boolean isCentrifugeStructure(Location centerLocation) {
        // Simplified - single block centrifuge as per requirements
        return true;
    }
    
    public boolean startCentrifuge(Location location) {
        if (!isCentrifugeStructure(location)) {
            return false;
        }
        
        if (activeCentrifuges.containsKey(location)) {
            return false; // Already running
        }
        
        activeCentrifuges.put(location, System.currentTimeMillis());
        return true;
    }
    
    private void completeCentrifuge(Location location) {
        // Generate 1-5 uranium dust
        int amount = random.nextInt(5) + 1;
        ItemStack uraniumDust = NexoItems.itemFromId("uranium_dust").build();
        uraniumDust.setAmount(amount);
        
        // Drop the items at the centrifuge location
        location.getWorld().dropItemNaturally(location.add(0, 1, 0), uraniumDust);
    }
    
    public boolean isCentrifugeActive(Location location) {
        return activeCentrifuges.containsKey(location);
    }
    
    public long getRemainingTime(Location location) {
        if (!activeCentrifuges.containsKey(location)) {
            return 0;
        }
        
        long startTime = activeCentrifuges.get(location);
        long elapsed = System.currentTimeMillis() - startTime;
        return Math.max(0, 300000 - elapsed); // 5 minutes in milliseconds
    }
}