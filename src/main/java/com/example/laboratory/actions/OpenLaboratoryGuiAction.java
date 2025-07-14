package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.LaboratoryGUI;
import com.nexomc.nexo.api.NexoBlockInteractiveAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OpenLaboratoryGuiAction extends NexoBlockInteractiveAction {
    
    @Override
    public void execute(Player player, Block block) {
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new LaboratoryGUI(plugin, player).open();
    }
    
    @Override
    public String getType() {
        return "open_laboratory_gui";
    }
}