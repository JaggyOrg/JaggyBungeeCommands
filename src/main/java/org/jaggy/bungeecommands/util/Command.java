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

package org.jaggy.bungeecommands.util;

import java.util.ArrayList;

public class Command {
    private String type;
    private String name;
    private ArrayList<String> commands;

    public Command(String type, ArrayList<String> commands) {
        this.name = name;
        this.type = type;
        this.commands = commands;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public String getType() {
        return type;
    }
}
