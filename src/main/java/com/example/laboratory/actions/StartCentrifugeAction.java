package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.nexomc.nexo.api.NexoBlockInteractiveAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class StartCentrifugeAction extends NexoBlockInteractiveAction {
    
    @Override
    public void execute(Player player, Block block) {
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        
        if (plugin.getCentrifugeManager().startCentrifuge(block.getLocation())) {
            player.sendMessage("§aЦентрифуга запущена! Ожидайте 5 минут.");
        } else if (plugin.getCentrifugeManager().isCentrifugeActive(block.getLocation())) {
            long remaining = plugin.getCentrifugeManager().getRemainingTime(block.getLocation());
            player.sendMessage("§eЦентрифуга уже работает. Осталось: " + (remaining / 1000) + " секунд.");
        } else {
            player.sendMessage("§cОшибка запуска центрифуги!");
        }
    }
    
    @Override
    public String getType() {
        return "start_centrifuge";
    }
}