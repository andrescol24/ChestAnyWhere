package co.andrescol.mc.plugin.chestanywhere.data;

import co.andrescol.mc.library.plugin.APlugin;
import co.andrescol.mc.plugin.chestanywhere.ChestAnyWherePlugin;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class YamlDataManager {

	/**
	 * Create the instance
	 *
	 */
	private YamlDataManager() {}

	/**
	 * Save the storage information
	 * 
	 * @param content Storage
	 */
	public void update(ChestAnyWhere content, Inventory inventory) {
		if (content.hasChanged(inventory)) {
			content.setContent(inventory.getContents());
			File playerFile = new File(this.getLocation(), content.getUuid() + ".yml");
			try {
				YamlConfiguration yaml = content.toYaml();
				yaml.save(playerFile);
			} catch (IOException e) {
				APlugin.getInstance().error("The data of {} cannot be saved", e, content.getName());
			}
		}
	}

	/**
	 * Read a File and load to object
	 * 
	 * @param player the player
	 * @return the playerStorage or null if not exist
	 */
	public ChestAnyWhere get(HumanEntity player) {
		File location = this.getLocation();
		File playerFile = new File(location, player.getUniqueId() + ".yml");
		if (playerFile.exists()) {
			try {
				YamlConfiguration yaml = new YamlConfiguration();
				yaml.load(playerFile);
				return new ChestAnyWhere(yaml);
			} catch (IOException | InvalidConfigurationException e) {
				APlugin.getInstance().error("Error reading the {} uuid: {} storage", e, player.getName(), player.getUniqueId());
			}
		}
		return new ChestAnyWhere(player);
	}
	
	private File getLocation() {
		ChestAnyWherePlugin plugin = APlugin.getInstance();
		PluginConfiguration configuration = APlugin.getConfigurationObject();
		String path = configuration.getStoragePath().replace("PLUGIN_DATA_FOLDER", plugin.getDataFolder().getPath());
		File location = new File(path);
		if (!location.exists()) {
			boolean created = location.mkdir();
			if (created) {
				plugin.info("The Storage dir was created in {}", location.getAbsolutePath());
			}
		}
		return location;
	}

	// ===================== STATICS =================================
	private static YamlDataManager instance;

	public static YamlDataManager getInstance() {
		if (instance == null) {
			instance = new YamlDataManager();
		}
		return instance;
	}
}
