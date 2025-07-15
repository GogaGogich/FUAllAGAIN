package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.LaboratoryGUI;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import com.nexomc.nexo.mechanics.custom_block.CustomBlockMechanic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpenLaboratoryGuiAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        CustomBlockMechanic mechanic = event.getMechanic();
        String id = mechanic.getName();
        
        if (!id.equalsIgnoreCase("laboratory_terminal")) {
            return;
        }
        
        Player player = event.getPlayer();
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new LaboratoryGUI(plugin, player).open();
    }
}