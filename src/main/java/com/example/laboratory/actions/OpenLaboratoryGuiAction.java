package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.LaboratoryGUI;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpenLaboratoryGuiAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        if (!"laboratory_terminal".equals(event.getBlockData().getId())) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new LaboratoryGUI(plugin, player).open();
    }
}