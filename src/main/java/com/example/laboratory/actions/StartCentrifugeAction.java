package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StartCentrifugeAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        if (!"centrifuge_block".equals(event.getBlockData().getId())) {
            return;
        }
        
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
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
}