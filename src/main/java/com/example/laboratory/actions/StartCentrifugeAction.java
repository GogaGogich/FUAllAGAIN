package com.example.laboratory.actions;

import com.example.laboratory.LaboratoryPlugin;
import com.nexomc.nexo.api.events.custom_block.NexoBlockInteractEvent;
import com.nexomc.nexo.mechanics.custom_block.CustomBlockMechanic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StartCentrifugeAction implements Listener {
    
    @EventHandler
    public void onBlockInteract(NexoBlockInteractEvent event) {
        CustomBlockMechanic mechanic = event.getMechanic();
        String id = mechanic.getName();
        
        if (!id.equalsIgnoreCase("centrifuge_block")) {
            return;
        }
        
        Player player = event.getPlayer();
        LaboratoryPlugin plugin = LaboratoryPlugin.getInstance();
        
        if (plugin.getCentrifugeManager().startCentrifuge(event.getBlock().getLocation())) {
            player.sendMessage("§aЦентрифуга запущена! Ожидайте 5 минут.");
        } else if (plugin.getCentrifugeManager().isCentrifugeActive(event.getBlock().getLocation())) {
            long remaining = plugin.getCentrifugeManager().getRemainingTime(event.getBlock().getLocation());
            player.sendMessage("§eЦентрифуга уже работает. Осталось: " + (remaining / 1000) + " секунд.");
        } else {
            player.sendMessage("§cОшибка запуска центрифуги!");
        }
    }
}