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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javasuns.profiler.asus.model.ProfileManager.Profile;

public class PropertyManager {
	private static Properties properties;
	private static Path propFile;
	private final static String version = "1.2";
	
	// Load Application Properties
	static {
		properties = new Properties();
		loadDefaultProperties();
		propFile = Paths.get("application.properties"); 
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
		return version;
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
	} // loadDefaultProperties()
	
	public static void saveProfile(Profile profile) {
		try {
			String content = new String(Files.readAllBytes(propFile),StandardCharsets.UTF_8);
			content = content.replaceAll("profile.active=.+", "profile.active=" + profile.name().toLowerCase());
			Files.write(propFile, content.getBytes(StandardCharsets.UTF_8));
			properties.setProperty("profile.active", profile.name());
		} catch (IOException e) { e.printStackTrace(); }
	} // saveProfile()
	
	public static Profile getActiveProfile() {
		return Profile.valueOf(getProperty("profile.active").toUpperCase());
	} // getActiveProfile()
	
} // class PropertyManager
