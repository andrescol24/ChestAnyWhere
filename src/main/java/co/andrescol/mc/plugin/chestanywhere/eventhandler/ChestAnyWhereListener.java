package co.andrescol.mc.plugin.chestanywhere.eventhandler;

import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhereHolder;
import co.andrescol.mc.plugin.chestanywhere.data.ChestAnyWhere;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Listener for the inventories interaction. It validate if the invetory is a
 * Storage
 * 
 * @author andrescol24
 *
 */
public class ChestAnyWhereListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void inventoryInteract(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		InventoryHolder holder = inventory.getHolder();
		if (holder instanceof ChestAnyWhereHolder) {
			HumanEntity player = event.getWhoClicked();
			YamlDataManager dataManager = YamlDataManager.getInstance();
			ChestAnyWhere content = dataManager.get(player);
			dataManager.update(content, inventory);
		}
	}
}
