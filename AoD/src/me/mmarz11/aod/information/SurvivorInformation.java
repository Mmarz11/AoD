package me.mmarz11.aod.information;

import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;

import org.bukkit.configuration.ConfigurationSection;

public class SurvivorInformation extends TeamInformation {
	public Kit juggernaut;

	public SurvivorInformation(Map map, ConfigurationSection config) {
		super(map, config);

		ConfigurationSection section = config
				.getConfigurationSection("Kit.Juggernaut");
		
		String key = (String) section.getKeys(false).toArray()[0];
		juggernaut = new Kit(key, section.getStringList(key));
	}
}
