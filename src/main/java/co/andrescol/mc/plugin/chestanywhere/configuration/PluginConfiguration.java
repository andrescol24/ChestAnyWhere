package co.andrescol.mc.plugin.chestanywhere.configuration;

import co.andrescol.mc.library.configuration.AConfigurationKey;
import co.andrescol.mc.library.configuration.AConfigurationObject;

import java.util.List;

/**
 * Singleton class that save the configuration
 *
 * @author andrescol24
 */
public class PluginConfiguration implements AConfigurationObject {

    @AConfigurationKey("storage-path")
    private String storagePath;

    public String getStoragePath() {
        return storagePath;
    }
}
