package me.mmarz11.aod.information;

import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;

import org.bukkit.configuration.ConfigurationSection;

public class HunterInformation extends TeamInformation {
	public Kit first;
	public Kit free;
	
	public boolean oneKitPerRound;
	public int kitResetTime;
	
	public boolean canUseLocate;
	public boolean moveFirstHunter;
	
	public HunterInformation(Map map, ConfigurationSection config) {
		super(map, config);
		
		ConfigurationSection section = config
				.getConfigurationSection("Kit.First");
		
		String key = (String) section.getKeys(false).toArray()[0];
		first = new Kit(key, config.getStringList(key));
		
		section = config.getConfigurationSection("Kit.Free");
		key = (String) section.getKeys(false).toArray()[0];
		free = new Kit(key, config.getStringList(key));
		
		oneKitPerRound = config.getBoolean("Kit.OneKitPerRound");
		kitResetTime = config.getInt("Kit.KitResetTime");
		
		canUseLocate = config.getBoolean("CanUseLocate");
		moveFirstHunter = config.getBoolean("MoveFirstHunter");
	}
}
