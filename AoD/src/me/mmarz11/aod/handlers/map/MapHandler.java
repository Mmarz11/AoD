package me.mmarz11.aod.handlers.map;

import java.util.ArrayList;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.handlers.ConfigHandler;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class MapHandler {
	public Location lobbySpawn;

	public Map previousMap;
	public Map currentMap;
	public Map nextMap;

	private ArrayList<Map> enabled;
	private ArrayList<Map> disabled;

	public MapHandler() {
		this.enabled = new ArrayList<Map>();
		this.disabled = new ArrayList<Map>();
	}

	public void init() {
		String worldName = AoD.inst.handlers.configHandler.getConfig()
				.getString("lobby.world");

		World world = AoD.inst.getServer().getWorld(worldName);
		FileConfiguration config = AoD.inst.handlers.configHandler.getConfig();
		double x = config.getDouble("lobby.location.x");
		double y = config.getDouble("lobby.location.y");
		double z = config.getDouble("lobby.location.z");

		lobbySpawn = new Location(world, x, y, z);
		world.setSpawnFlags(false, false);

		for (String map : config.getStringList("maps.enabled")) {
			enableMap(map);
		}

		for (String map : config.getStringList("maps.disabled")) {
			disableMap(map);
		}
	}

	public void enableMap(String name) {
		ConfigHandler configHandler = AoD.inst.handlers.configHandler;

		Map map = new Map(AoD.inst, name);
		if (!enabled.contains(map)) {
			if (disabled.contains(map)) {
				disabled.remove(map);
			}
			enabled.add(map);
			configHandler.addMapConfig(map);
		}
	}

	public void disableMap(String name) {
		ConfigHandler configHandler = AoD.inst.handlers.configHandler;

		Map map = new Map(AoD.inst, name);
		if (!disabled.contains(map)) {
			if (enabled.contains(map)) {
				enabled.remove(map);
			}
			disabled.add(map);
			configHandler.addMapConfig(map);
		}
	}

	public void setRandomCurrentMap() {
		currentMap = enabled.get((int) (Math.random() * enabled.size()));
	}
}
