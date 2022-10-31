package co.andrescol.mc.plugin.chestanywhere.eventhandler;

import co.andrescol.mc.plugin.chestanywhere.data.StorageContent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import co.andrescol.mc.plugin.chestanywhere.Storage;
import co.andrescol.mc.plugin.chestanywhere.StorageHolder;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;

/**
 * Listener for the inventories interaction. It validate if the invetory is a
 * Storage
 * 
 * @author andrescol24
 *
 */
public class StorageInteractListener implements Listener {

	private final Storage plugin;

	/**
	 * Create the listener
	 * 
	 * @param plugin plugin
	 */
	public StorageInteractListener(Storage plugin) {
		this.plugin = plugin;
	}

	/**
	 * The event have low priority because no all inventories are Storage. The
	 * storage inventories are few. But this event can be cancelled
	 * 
	 * @param event Event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void storageInteract(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		InventoryHolder holder = inventory.getHolder();
		if (holder instanceof StorageHolder) {
			// Save the content
			HumanEntity player = event.getPlayer();
			YamlDataManager dataManager = YamlDataManager.getInstance(plugin);
			StorageContent savedContent = dataManager.get(player);

			// If the content has changet, save it
			if (savedContent.hasChanged(inventory)) {
				savedContent.setContent(inventory.getContents());
				dataManager.put(player, savedContent);
			}
		}
	}
}
