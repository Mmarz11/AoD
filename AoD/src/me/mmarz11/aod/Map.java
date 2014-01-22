package me.mmarz11.aod;

import java.util.logging.Level;

import me.mmarz11.aod.information.MapInformation;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class Map {
	private AoD plugin;
	
	public World world;
	
	public MapInformation mapInformation;
	
	public Map(AoD plugin, String name) {
		this.plugin = plugin;
		
		this.plugin.getServer().createWorld(new WorldCreator(name));
		this.world = plugin.getServer().getWorld(name);
		
		if (this.world == null) {
			this.plugin.handlers.mapHandler.disableMap(this.getName());
			this.plugin.getLogger().log(Level.SEVERE, "World " + name + " Does Not Exist");
		}
	}
	
	public String getName() {
		return world.getName();
	}
	
	public void initMapInformation(FileConfiguration config) {
		mapInformation = new MapInformation(this, config);
	}
}
