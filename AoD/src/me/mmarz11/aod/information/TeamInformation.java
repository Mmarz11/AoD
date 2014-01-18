package me.mmarz11.aod.information;

import java.util.ArrayList;
import java.util.List;

import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class TeamInformation {
	public String spawnType;
	public Location spawn;
	
	public boolean buildEnabled;
	public List<String> buildAllowed;
	public List<String> buildRestricted;
	public List<String> buildAlways;
	
	public boolean breakEnabled;
	public List<String> breakAllowed;
	public List<String> breakRestricted;
	public List<String> breakAlways;
	
	public List<Kit> starting;
	public boolean chooseKit;
	public boolean resetInventory;
	
	public int fallReduction;
	public boolean canDrown;
	
	public List<String> defaultEffects;
	public List<String> onAttackEffects;
	public List<String> onDefendEffects;
	
	public TeamInformation(Map map, ConfigurationSection config) {
		spawnType = config.getString("SpawnType");
		double x = config.getDouble("Spawn.x");
		double y = config.getDouble("Spawn.y");
		double z = config.getDouble("Spawn.z");
		spawn = new Location(map.world, x, y, z);
		
		buildEnabled = config.getBoolean("Build.Enabled");
		buildAllowed = config.getStringList("Build.Allowed");
		buildRestricted = config.getStringList("Build.Restricted");
		buildAlways = config.getStringList("Build.Always");
		
		breakEnabled = config.getBoolean("Break.Enabled");
		breakAllowed = config.getStringList("Break.Allowed");
		breakRestricted = config.getStringList("Break.Restricted");
		breakAlways = config.getStringList("Break.Always");
		
		starting = new ArrayList<Kit>();
		ConfigurationSection section = config.getConfigurationSection("Kit.Start");
		for(String key : section.getKeys(false)) {
			starting.add(new Kit(key, section.getStringList(key)));
		}
		chooseKit = config.getBoolean("Kit.ChooseKit");
		resetInventory = config.getBoolean("Kit.ResetInventory");
		
		fallReduction = config.getInt("FallReduction");
		canDrown = config.getBoolean("CanDrown");
		
		defaultEffects = config.getStringList("Effects.Default");
		onAttackEffects = config.getStringList("Effects.OnAttack");
		onDefendEffects = config.getStringList("Effects.OnDefend");
	}
}
