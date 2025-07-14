package com.example.laboratory.actions;

import com.nexomc.nexo.api.NexoBlockInteractiveAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LoadLaboratoryResourcesAction extends NexoBlockInteractiveAction {
    
    @Override
    public void execute(Player player, Block block) {
        player.sendMessage("§aРесурсы загружены в лабораторию!");
    }
    
    @Override
    public String getType() {
        return "load_laboratory_resources";
    }
}