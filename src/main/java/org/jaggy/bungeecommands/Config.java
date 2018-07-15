/*
 * Copyright (C) 2018 Jaggy Enterprises
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.jaggy.bungeecommands;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jaggy.bungeecommands.util.Command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Config {
    /**
     * Reference to plugin main class
     */
    private final Main plugin;
    /**
     * Container for the configuration class
     */
    private Configuration config;
    /**
     * Command storage for registering commands
     */
    private HashMap<String,Command> commands = new HashMap<String, Command>();

    /**
     * Config Constructor
     * @param main Passes parent class
     */
    public Config(Main main) {
        plugin = main;
        load();
    }

    /**
     * Loads the configuration file
     */
    public void load() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                plugin.log.log(Level.SEVERE,e.getMessage(),e);
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            plugin.log.log(Level.SEVERE,e.getMessage(),e);
        }
        try {
            parse();
        } catch (ParseError parseError) {
            plugin.log.log(Level.SEVERE,parseError.getMessage());
            plugin.enable = false;
        }
    }

    /**
     * Parse config
     */
    public void parse() throws ParseError {
        ArrayList list = (ArrayList) config.get("Commands");

        for (Object object: list) {
            Map<String, Object> command = (Map<String, Object>) object;
            Object[] keys = command.keySet().toArray();
            String type = null;
            String name = (String) keys[0];
            ArrayList<String> cmds = new ArrayList<String>();
            for (Object obj: keys) {
                String key = (String) obj;
                Object value = command.get(key);

                if (key.toLowerCase().equals("type") && value.toString().toUpperCase().equals("ALIAS")) {
                    if (!command.containsKey("command")) {
                        throw new ParseError("ERROR: The command \""
                                +name.toUpperCase()+"\" REQUIRES a command: attribute for the alias.");
                    }
                }
                if (key.toLowerCase().equals("type") && value.toString().toUpperCase().equals("COMMANDS")) {
                    if (!command.containsKey("commands")) {
                        throw new ParseError("ERROR: The command \""
                                +name.toUpperCase()+"\" REQUIRES a commands: attribute for the command.");
                    }
                }
                if (key.toLowerCase().equals("type")) type = (String) value;
                if (key.toLowerCase().equals("command")) cmds.add((String) value);
                if (key.toLowerCase().equals("commands")) {
                    for (String c: (ArrayList<String>)value) {
                        cmds.add(c);
                    }
                }
            }
            commands.put(name,new Command(type, cmds));

        }
    }

    /**
     * Gets the parsed commands
     * @return Map<String, Command> structure
     */
    public Map<String,Command> getCommands() {
        return commands;
    }
}
