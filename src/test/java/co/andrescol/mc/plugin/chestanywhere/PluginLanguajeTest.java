package co.andrescol.mc.plugin.chestanywhere;

import static org.junit.Assert.assertEquals;

import co.andrescol.mc.plugin.chestanywhere.configuration.PluginLanguaje;
import org.junit.Test;

public class PluginLanguajeTest {

	@Test
	public void testPropertiesKey() {
		PluginLanguaje.LanguajeProperty property = PluginLanguaje.LanguajeProperty.OPEN_PLAYER_NOTFOUND;
		String key = property.getKey();
		assertEquals("open.player.notfound", key);
	}
}
