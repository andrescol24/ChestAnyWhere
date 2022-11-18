package co.andrescol.mc.plugin.chestanywhere;

import java.io.File;
import java.util.logging.Level;

import co.andrescol.mc.plugin.chestanywhere.command.OpenStorageCommand;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginLanguaje;
import co.andrescol.mc.plugin.chestanywhere.eventhandler.StorageInteractListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * plugin's main class
 * 
 * @author xX_andrescol_Xx
 *
 */
public class ChestAnyWherePlugin extends JavaPlugin implements ChestAnyWhere {
	
	Listener storageInteractLister;

	@Override
	public void onEnable() {
		this.chargeConfiguration();
		this.getCommand("sopen").setExecutor(new OpenStorageCommand(this));
		this.getServer().getPluginManager().registerEvents(new StorageInteractListener(this), this);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public void info(String message, Object... replacements) {
		message = PluginLanguaje.replaceValues(message, replacements);
		this.getLogger().info(message);
	}

	@Override
	public void warn(String message, Object... replacements) {
		message = PluginLanguaje.replaceValues(message, replacements);
		this.getLogger().warning(message);

	}

	@Override
	public void error(String message, Throwable exception, Object... replacements) {
		message = PluginLanguaje.replaceValues(message, replacements);
		this.getLogger().log(Level.SEVERE, message, exception);

	}

	@Override
	public FileConfiguration getFileConfiguration() {
		return this.getConfig();
	}

	@Override
	public File getPluginFolder() {
		return this.getDataFolder();
	}

	/**
	 * Load and save the configuration files
	 */
	private void chargeConfiguration() {
		getConfig().options().copyDefaults(true);
		File config = new File(getDataFolder(), "config.yml");
		File lang = new File(getDataFolder(), "lang.properties");
		try {
			if (!config.exists()) {
				saveDefaultConfig();
			}
			if (!lang.exists()) {
				saveResource("lang.properties", false);
			}
		} catch (Exception e) {
			this.error("Can not load the configuration", e);
		}
	}
}
