package me.mmarz11.aod.handlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {
	private FileConfiguration config;
	private File configFile;

	private HashMap<String, FileConfiguration> maps;

	private FileConfiguration stats;
	private File statsFile;

	public ConfigHandler() {
		maps = new HashMap<String, FileConfiguration>();
	}

	public void init() {
		if (!AoD.inst.getDataFolder().exists()) {
			AoD.inst.getDataFolder().mkdir();
		}

		configFile = new File(AoD.inst.getDataFolder(), "config.yml");
		config = AoD.inst.getConfig();

		config.options().copyDefaults(true);
		saveConfig();

		statsFile = new File(AoD.inst.getDataFolder(), "stats.yml");
		stats = YamlConfiguration.loadConfiguration(statsFile);

		saveStatConfig();
	}

	public void saveAll() {
		saveStatConfig();
		saveConfig();
	}

	public FileConfiguration getStatsConfig() {
		return stats;
	}

	public void saveStatConfig() {
		try {
			stats.save(statsFile);
		} catch (IOException e) {
			AoD.inst.getServer().getLogger()
					.log(Level.SEVERE, "Could not save stats config.");
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException exception) {
			AoD.inst.getServer().getLogger()
					.log(Level.SEVERE, "Could not save config.yml");
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void addMapConfig(Map map) {
		File file = new File(AoD.inst.getDataFolder(), "maps/" + map.getName()
				+ ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		try {
			config.save(file);
		} catch (IOException exception) {
			AoD.inst.getServer()
					.getLogger()
					.log(Level.SEVERE,
							"Could not save map config of: " + file.getName());
		}
		maps.put(map.getName(), config);
		map.initMapInformation(config);
	}
}
