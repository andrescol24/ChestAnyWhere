package co.andrescol.mc.plugin.chestanywhere;

import co.andrescol.mc.library.plugin.APlugin;
import co.andrescol.mc.plugin.chestanywhere.command.ChestAnyWhereCommand;
import co.andrescol.mc.plugin.chestanywhere.configuration.PluginConfiguration;
import co.andrescol.mc.plugin.chestanywhere.eventhandler.ChestAnyWhereListener;
import org.bukkit.event.HandlerList;

/**
 * plugin's main class
 * 
 * @author xX_andrescol_Xx
 *
 */
public class ChestAnyWherePlugin extends APlugin<PluginConfiguration> {

	@Override
	public void onEnable() {
		this.getCommand("copen").setExecutor(new ChestAnyWhereCommand());
		this.getServer().getPluginManager().registerEvents(new ChestAnyWhereListener(), this);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}

	@Override
	protected void initializeCustomConfiguration() {
		this.configurationObject = new PluginConfiguration();
	}
}
