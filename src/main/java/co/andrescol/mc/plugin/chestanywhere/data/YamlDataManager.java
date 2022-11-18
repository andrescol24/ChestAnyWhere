package co.andrescol.mc.plugin.chestanywhere.data;

import java.io.File;
import java.io.IOException;

import co.andrescol.mc.plugin.chestanywhere.configuration.PluginConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;

import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhere;

public class YamlDataManager {

	private ChestAnyWhere plugin;

	/**
	 * Create the instance
	 * 
	 * @param plugin plugin
	 */
	private YamlDataManager(ChestAnyWhere plugin) {
		this.plugin = plugin;	
	}

	/**
	 * Save the storage information
	 * 
	 * @param storage Storage
	 */
	public boolean put(HumanEntity player, StorageContent storage) {
		File location = this.getLocation();
		File playerFile = new File(location, player.getUniqueId() + ".yml");
		try {
			YamlConfiguration yaml = storage.toYaml();
			yaml.save(playerFile);
			return true;
		} catch (IOException e) {
			this.plugin.error("The data of {} cannot be saved", e, storage.getName());
			return false;
		}
	}

	/**
	 * Read a File and load to object
	 * 
	 * @param player the player
	 * @return the playerStorage or null if not exist
	 */
	public StorageContent get(HumanEntity player) {
		File location = this.getLocation();
		File playerFile = new File(location, player.getUniqueId() + ".yml");
		if (playerFile.exists()) {
			try {
				YamlConfiguration yaml = new YamlConfiguration();
				yaml.load(playerFile);
				return new StorageContent(yaml);
			} catch (IOException | InvalidConfigurationException e) {
				this.plugin.error("Error reading the {} uuid: {} storage", e, player.getName(), player.getUniqueId());
			}
		}
		return new StorageContent(player);
	}
	
	private File getLocation() {
		PluginConfiguration configuration = PluginConfiguration.getInstance(plugin);
		String path = configuration.getStoragePath().replace("PLUGIN_DATA_FOLDER", plugin.getPluginFolder().getPath());
		File location = new File(path);
		if (!location.exists()) {
			boolean created = location.mkdir();
			if (created) {
				this.plugin.info("The Storage dir was created in {}", location.getAbsolutePath());
			}
		}
		return location;
	}

	// ===================== STATICS =================================
	private static YamlDataManager instance;

	public static YamlDataManager getInstance(ChestAnyWhere plugin) {
		if (instance == null) {
			instance = new YamlDataManager(plugin);
		}
		return instance;
	}
}
