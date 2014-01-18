package me.mmarz11.aod.information;

import java.util.List;

import me.mmarz11.aod.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class MapInformation {
	public Map map;
	public FileConfiguration config;

	public SurvivorInformation survivor;
	public HunterInformation hunter;

	public String name;
	public String author;

	public int build;
	public int game;
	public int spawnProtect;
	public String gamemode;

	public int time;
	public int weather;
	public Location center;
	public int size;
	public int skyLimit;

	public boolean mobsEnabled;
	public List<String> mobsAllowed;
	public List<String> mobsRestricted;

	public MapInformation(Map map, FileConfiguration config) {
		this.map = map;
		this.config = config;

		this.survivor = new SurvivorInformation(map,
				config.getConfigurationSection("Team.Survivor"));
		this.hunter = new HunterInformation(map,
				config.getConfigurationSection("Team.Hunter"));

		name = config.getString("Map.Name");
		author = config.getString("Map.Author");

		build = config.getInt("Round.Build");
		game = config.getInt("Round.Game");
		spawnProtect = config.getInt("Round.SpawnProtect");
		gamemode = config.getString("Round.Gamemode");

		time = config.getInt("World.Time");
		weather = config.getInt("World.Weather");
		double x = config.getDouble("World.Center.x");
		double y = config.getDouble("World.Center.y");
		double z = config.getDouble("World.Center.z");
		center = new Location(map.world, x, y, z);
		size = config.getInt("World.Size");
		skyLimit = config.getInt("World.SkyLimit");

		mobsEnabled = config.getBoolean("World.Mob.Enabled");
		mobsAllowed = config.getStringList("World.Mob.Allowed");
		mobsRestricted = config.getStringList("World.Mob.Restricted");
	}
}
