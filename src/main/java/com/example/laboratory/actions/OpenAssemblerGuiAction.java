package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.AssemblerGUI;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import com.nexomc.nexo.mechanics.custom_block.CustomBlockMechanic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OpenAssemblerGuiAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        CustomBlockMechanic mechanic = event.getMechanic();
        String id = mechanic.getModel().asString();
        
        if (!id.contains("assembler")) {
            return;
        }
        
        Player player = event.getPlayer();
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new AssemblerGUI(plugin, player).open();
    }
}