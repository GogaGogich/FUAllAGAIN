package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.example.laboratory.gui.TeleporterGUI;
import com.nexomc.nexo.api.NexoBlockInteractiveAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OpenTeleporterGuiAction extends NexoBlockInteractiveAction {
    
    @Override
    public void execute(Player player, Block block) {
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        new TeleporterGUI(plugin, player, block.getLocation()).open();
    }
    
    @Override
    public String getType() {
        return "open_teleporter_gui";
    }
}