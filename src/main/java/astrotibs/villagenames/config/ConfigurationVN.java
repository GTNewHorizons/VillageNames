package astrotibs.villagenames.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * Adaptation of get methods without the default values being printed in the comments,
 * to save from loading up the config file with too much distracting text.
 */
public class ConfigurationVN extends Configuration {

    public ConfigurationVN(File configFile) {
        super(configFile);
    }

    public String getStringWithoutDefaultsInComment(String name, String category, String defaultValue, String comment) {
        Property prop = this.get(category, name, defaultValue);
        prop.setLanguageKey(name);
        prop.setValidationPattern(null);
        prop.comment = comment;
        return prop.getString();
    }

    public String[] getStringListWithoutDefaultsInComment(String name, String category, String[] defaultValue,
        String comment) {
        Property prop = this.get(category, name, defaultValue);
        prop.setLanguageKey(name);
        prop.setValidValues((String[]) null);
        prop.comment = comment;
        return prop.getStringList();
    }
}
