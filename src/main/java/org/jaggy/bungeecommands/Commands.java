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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

public class Commands extends Command {
    private final ArrayList<String> commands;
    private final String type;
    private final Main plugin;
    private final String name;

    public Commands(Main main, String command, String type, ArrayList<String> commands) {
        super(command, "jaggybungeecommands." + command);
        this.commands = commands;
        this.name = command;
        this.type = type;
        this.plugin = main;
    }

    public void execute(CommandSender sender, String[] strings) {
        if (sender.hasPermission("jaggybungeecommands." + name)) {
            for (String command : commands) {
                plugin.getProxy().getPluginManager().dispatchCommand(sender, command);
            }
        } else {
            TextComponent msg = new TextComponent("You do not have permissionss to use this command.");
            msg.setColor(ChatColor.GOLD);
            msg.setBold(true);
            sender.sendMessage();
        }
    }
}
