package me.mmarz11.aod.information;

import me.mmarz11.aod.AoD;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerInformation {
	private AoD plugin;

	public String name;

	public String rank;
	public int brains;
	public int survivorKills;
	public int survivorDeaths;
	public int hunterKills;
	public int hunterDeaths;

	public PlayerInformation(AoD plugin, String name,
			ConfigurationSection section) {
		this.plugin = plugin;
		this.name = name;

		if (section == null) {
			rank = "default";
			brains = 0;
			survivorKills = 0;
			survivorDeaths = 0;
			hunterKills = 0;
			hunterDeaths = 0;
		} else {
			rank = section.getString("rank");
			brains = section.getInt("brains");
			survivorKills = section.getInt("survivor.kills");
			survivorDeaths = section.getInt("survivor.deaths");
			hunterKills = section.getInt("hunter.kills");
			hunterDeaths = section.getInt("hunterDeaths");
		}
	}

	public void save() {
		FileConfiguration config = this.plugin.handlers.configHandler
				.getStatsConfig();

		config.set(name + ".rank", rank);
		config.set(name + ".brains", brains);
		config.set(name + ".survivor.kills", survivorKills);
		config.set(name + ".survivor.deaths", survivorDeaths);
		config.set(name + ".hunter.kills", hunterKills);
		config.set(name + ".hunter.deaths", hunterDeaths);
	}
}
