/*
 * Copyright (C) 2022-2023 AsusProfiler 
 * Giannos Hadjipanayis
 * All rights reserved.
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
 */

package javasuns.profiler.asus.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {
	private static Properties properties;
	
	// Load Application Properties
	static {
		properties = new Properties();
		loadDefaultProperties();
		Path propFile = Paths.get("application.properties"); 
		try {
			properties.load(Files.newBufferedReader(propFile));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	} // getProperty()
	
	public static String getProjectPath() {
		return "/" + properties.getProperty("project.package").replace(".", "/");
	} // getProjectPath()
	
	public static String getVersion() {
		return properties.getProperty("project.version");
	} // getVersion()
	
	public static Map<String,String> getProperties(String prefix) {
		prefix += ".";
		var prefixProperties = new LinkedHashMap<String,String>();
		var enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                if (key.startsWith(prefix))
                	prefixProperties.put(key.replace(prefix, ""), value);
            }
        return prefixProperties;
	} // getProperties()
	
	private static void loadDefaultProperties() {
		String userDir = System.getProperty("user.dir");
		properties.put("tools.ryzenadj", userDir + "/tools/ryzenadj/ryzenadj.exe");
		properties.put("tools.atrofac",  userDir + "/tools/atrofac/atrofac-cli.exe");	
		properties.put("tools.nvoc",     userDir + "/tools/nvoc/nvoc.exe");
		properties.put("tools.powermode",userDir + "/tools/PowerMode/PowerMode.exe");
		properties.put("tools.powercfg", "C:/Windows/System32/powercfg.exe");
		properties.put("tools.powershell", "powershell.exe");
	}
} // class Properties
