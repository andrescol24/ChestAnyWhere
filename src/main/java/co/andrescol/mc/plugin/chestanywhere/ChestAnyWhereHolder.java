package co.andrescol.mc.plugin.chestanywhere;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Holder than represent a Storage. It is used for diference a normal chest to a
 * Storage
 * 
 * @author andres.morales
 *
 */
public class ChestAnyWhereHolder implements InventoryHolder {

	@Override
	public Inventory getInventory() {
		throw new UnsupportedOperationException();
	}
}
