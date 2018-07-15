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


import net.md_5.bungee.api.plugin.Plugin;
import org.jaggy.bungeecommands.util.Command;

import java.util.Map;
import java.util.logging.Logger;


public class Main extends Plugin {
    public Config config;
    public Logger log;
    public boolean enable = true;

    @Override
    public void onLoad() {
        log = this.getLogger();
        config = new Config(this);
    }

    @Override
    public void onEnable() {
        if (enable) {
            Map<String, Command> commands = config.getCommands();
            for(String command: commands.keySet()) {
                Command data = commands.get(command);
                getProxy().getPluginManager().registerCommand(this, new Commands(this,command,
                        data.getType(),data.getCommands()));
                log.info("Registered "+command+"!");
            }


        } else {
            log.severe("Could not parse config so JaggyBungeeCommands will not be enabled.");
        }
    }

}
