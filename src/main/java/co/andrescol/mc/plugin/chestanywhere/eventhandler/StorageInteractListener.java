package co.andrescol.mc.plugin.chestanywhere.eventhandler;

import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhereHolder;
import co.andrescol.mc.plugin.chestanywhere.data.StorageContent;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Listener for the inventories interaction. It validate if the invetory is a
 * Storage
 * 
 * @author andrescol24
 *
 */
public class StorageInteractListener implements Listener {

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
		if (holder instanceof ChestAnyWhereHolder) {
			// Save the content
			HumanEntity player = event.getPlayer();
			YamlDataManager dataManager = YamlDataManager.getInstance();
			StorageContent savedContent = dataManager.get(player);

			// If the content has changet, save it
			if (savedContent.hasChanged(inventory)) {
				savedContent.setContent(inventory.getContents());
				dataManager.put(player, savedContent);
			}
		}
	}
}
