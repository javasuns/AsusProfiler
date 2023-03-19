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
	private final static String version = "1.3";
	
	// Load Application Properties
	static {
		properties = new Properties();
		loadDefaultProperties();
		propFile = Paths.get(getApplicationDir() + "/application.properties"); 
		try {
			properties.load(Files.newBufferedReader(propFile));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private static String getApplicationDir() {
		String property = System.getProperty("jpackage.app-path");
		String appDir = property != null ? Paths.get(property).getParent().toString() : ".";
		return appDir;
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	} // getProperty()
	
	public static String getProperty(String key, String defaultValue) {
		return (String) properties.getOrDefault(key, defaultValue);
	}
	
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
		String appDir = getApplicationDir();
		properties.put("tools.ryzenadj", appDir + "/tools/ryzenadj/ryzenadj.exe");
		properties.put("tools.atrofac",  appDir + "/tools/atrofac/atrofac-cli.exe");	
		properties.put("tools.nvoc",     appDir + "/tools/nvoc/nvoc.exe");
		properties.put("tools.powermode",appDir + "/tools/PowerMode/PowerMode.exe");
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
	
	public static void setProperty(String key, String value) {
		try {
			String content = new String(Files.readAllBytes(propFile),StandardCharsets.UTF_8);
			if(content.contains(key+"="))
				content = content.replaceAll(key+"=.+", key+"="+value);
			else
				content = content + "\n " + key+"="+value;
			Files.write(propFile, content.getBytes(StandardCharsets.UTF_8));
			properties.setProperty(key, value);
		} catch (IOException e) { e.printStackTrace(); }
	} // setProperty()
	
	public static void setProperty(String key, boolean value) {
		setProperty(key, String.valueOf(value));
	}
	
	public static String getAppExecutablePath() {
		return System.getProperty("jpackage.app-path");
	}
	
} // class PropertyManager
