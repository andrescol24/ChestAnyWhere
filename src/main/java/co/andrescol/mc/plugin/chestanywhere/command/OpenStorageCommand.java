package co.andrescol.mc.plugin.chestanywhere.command;

import java.util.LinkedList;
import java.util.List;

import co.andrescol.mc.plugin.chestanywhere.data.StorageContent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhere;
import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhereHolder;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginConfiguration;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginLanguaje;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginLanguaje.LanguajeProperty;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;

/**
 * This command executor make that the player open the inventory. This command
 * can be executed by players and console
 * 
 * @author andrescol24
 *
 */
public class OpenStorageCommand extends StorageCommand implements CommandExecutor, TabCompleter {

	public OpenStorageCommand(ChestAnyWhere plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String argumment2, String[] arguments) {

		// Checking permission or argumment 2 null or empty
		if (sender.hasPermission(command.getPermission())) {
			PluginLanguaje languaje = PluginLanguaje.getInstance(plugin);

			String name = this.getArgument(0, arguments);
			// Incorrect usage
			if (name == null) {
				this.sendMessage(sender, command.getUsage());
				return true;
			}
			
			// Reload configuration
			if(name.equals("reload") && sender.hasPermission("storage.reloadconfig")) {
				PluginConfiguration.getInstance(plugin).reloadConfig();
				this.sendMessage(sender, languaje.getMessage(LanguajeProperty.CONFIG_RELOADED));
				return true;
			}

			Player player = Bukkit.getPlayer(name);
			// if the player is offline
			if (player == null) {
				String message = languaje.getMessage(LanguajeProperty.OPEN_PLAYER_NOTFOUND, name);
				this.sendMessage(sender, message);
			} else {
				// Read the playerStorage (content)
				YamlDataManager dataManager = YamlDataManager.getInstance(plugin);
				StorageContent content = dataManager.get(player);
				Inventory inventory = Bukkit.createInventory(new ChestAnyWhereHolder(), 27,
						languaje.getMessage(LanguajeProperty.STORAGE_NAME));
				if (content.getContent() != null) {
					inventory.setContents(content.getContent());
				}
				player.openInventory(inventory);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> parameters = new LinkedList<>();
		String first = this.getArgument(0, args);
		if(first != null && first.startsWith("r")) {
			parameters.add("reload");
		}
		Bukkit.getOnlinePlayers().forEach(player -> parameters.add(player.getName()));
		return parameters;
	}

}
