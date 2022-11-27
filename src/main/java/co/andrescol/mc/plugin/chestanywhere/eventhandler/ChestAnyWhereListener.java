package co.andrescol.mc.plugin.chestanywhere.eventhandler;

import co.andrescol.mc.plugin.chestanywhere.ChestAnyWhereHolder;
import co.andrescol.mc.plugin.chestanywhere.data.ChestAnyWhere;
import co.andrescol.mc.plugin.chestanywhere.data.YamlDataManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
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
		this.saveInventory(event, event.getWhoClicked());
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void inventoryInteract(InventoryCloseEvent event) {
		this.saveInventory(event, event.getPlayer());
	}

	private void saveInventory(InventoryEvent event, HumanEntity player) {
		Inventory inventory = event.getInventory();
		InventoryHolder holder = inventory.getHolder();
		if (holder instanceof ChestAnyWhereHolder) {
			YamlDataManager dataManager = YamlDataManager.getInstance();
			ChestAnyWhere content = dataManager.get(player);
			dataManager.update(content, inventory);
		}
	}
}
