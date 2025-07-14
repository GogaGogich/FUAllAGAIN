package com.example.laboratory;

import com.example.laboratory.commands.RadiationCommand;
import com.example.laboratory.commands.TeleportCommand;
import com.example.laboratory.listeners.ItemInteractListener;
import com.example.laboratory.managers.CentrifugeManager;
import com.example.laboratory.managers.RadiationManager;
import com.example.laboratory.managers.ResearchManager;
import com.example.laboratory.managers.TeleportManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LaboratoryPlugin extends JavaPlugin {
    
    private static LaboratoryPlugin instance;
    private RadiationManager radiationManager;
    private ResearchManager researchManager;
    private TeleportManager teleportManager;
    private CentrifugeManager centrifugeManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Check if Nexo is available
        if (!getServer().getPluginManager().isPluginEnabled("Nexo")) {
            getLogger().severe("Nexo plugin not found! This plugin requires Nexo to function.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Initialize managers
        radiationManager = new RadiationManager(this);
        researchManager = new ResearchManager(this);
        teleportManager = new TeleportManager(this);
        centrifugeManager = new CentrifugeManager(this);
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new ItemInteractListener(this), this);
        
        // Register commands
        getCommand("teleport").setExecutor(new TeleportCommand(teleportManager));
        getCommand("radiation").setExecutor(new RadiationCommand(radiationManager));
        
        getLogger().info("Laboratory Plugin enabled successfully!");
        getLogger().info("Using Nexo API version: " + getServer().getPluginManager().getPlugin("Nexo").getDescription().getVersion());
    }
    
    @Override
    public void onDisable() {
        if (radiationManager != null) {
            radiationManager.shutdown();
        }
        getLogger().info("Laboratory Plugin disabled!");
    }
    
    public static LaboratoryPlugin getInstance() {
        return instance;
    }
    
    public RadiationManager getRadiationManager() {
        return radiationManager;
    }
    
    public ResearchManager getResearchManager() {
        return researchManager;
    }
    
    public TeleportManager getTeleportManager() {
        return teleportManager;
    }
    
    public CentrifugeManager getCentrifugeManager() {
        return centrifugeManager;
    }
}