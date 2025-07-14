package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.TeleporterGUI;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpenTeleporterGuiAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        if (!"teleporter".equals(event.getBlockData().getId())) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new TeleporterGUI(plugin, player, block.getLocation()).open();
    }
}