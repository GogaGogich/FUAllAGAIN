package com.example.laboratory.actions;

import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LoadLaboratoryResourcesAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        if (!"load_laboratory_resources".equals(event.getBlockData().getId())) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        player.sendMessage("§aРесурсы загружены в лабораторию!");
    }
}