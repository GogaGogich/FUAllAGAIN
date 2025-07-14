package com.example.laboratory.actions;

import com.nexomc.nexo.api.NexoBlockInteractiveAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LoadAssemblerResourcesAction extends NexoBlockInteractiveAction {
    
    @Override
    public void execute(Player player, Block block) {
        player.sendMessage("§aРесурсы загружены в сборщик!");
    }
    
    @Override
    public String getType() {
        return "load_assembler_resources";
    }
}