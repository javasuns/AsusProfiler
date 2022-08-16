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
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javasuns.profiler.asus.model.ProfileManager.Profile;

public class ProfileLoader extends Thread {
	private BooleanProperty running = new SimpleBooleanProperty();
	private String output;
	Profile profile;
	private StringBuilder sb = new StringBuilder();
	private Map<String, String> tools;

	public ProfileLoader() {
		tools = PropertyManager.getProperties("tools");
		start();
	} // Constructor Method
	
	public void loadProfile(Profile profile)  {
		this.profile = profile;
		running.set(true);
		synchronized(running) {
			running.notifyAll();
		}
	}
	
	public String getOutput() {
		return output;
	} // getOutput
	
	@Override
	public void run() {
		synchronized(running) {
			while(true) {
				try {
					if(!running.get()) {
						running.wait();
					}
					output = loadProfile();
					running.set(false);
				} catch (Exception e) { e.printStackTrace(); }
			} // while
		} //synchronized
	} // run
	

	private String loadProfile() {
		sb.setLength(0); // Clear StringBuilder
		var toolParams = PropertyManager.getProperties(profile + ".tool");
		toolParams.entrySet().stream().forEach(entry -> {
			try {
				String tool = getTool(entry.getKey());
				if (tool != null) {
					String cmd = tool + " " + entry.getValue();
					sb.append("[" + cmd + "]\n");
					sb.append(CmdManager.getInst().run(cmd) + "\n");
				} // if
				else
					System.err.println("Couldn't find tool for " + entry.getKey());
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		var activateWinServices = PropertyManager.getProperty(profile + ".win.services");
		Optional.ofNullable(activateWinServices).ifPresent(activated -> {
			Boolean activate = activateWinServices.toLowerCase().equals("disabled") ? false
					: activateWinServices.toLowerCase().equals("enabled") ? true : null;
			try {
				activateWinServices(activate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return sb.toString().trim();
	}

	private void activateWinServices(Boolean activate) throws Exception {
		if (activate == null)
			throw new Exception("Invalid value for Windows Services");
		var winServices = PropertyManager.getProperties("win.services.asus");
		winServices.entrySet().stream().forEach(entry -> {
			try {
				String cmd = "sc " + (activate ? "start " : "stop ") + entry.getValue();
				sb.append("[" + cmd + "]\n");
				sb.append(CmdManager.getInst().run(cmd) + "\n");
				cmd = "sc config " + entry.getValue() + " start=" + (activate ? "enabled" : "disabled");
				sb.append("[" + cmd + "]\n");
				sb.append(CmdManager.getInst().run(cmd) + "\n");
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	} // activateWinServices()

	private String getTool(String key) {
		return tools.get(key.replaceAll("\\.\\d$", ""));
	} // getTool()
	

	public BooleanProperty runningProperty() {
		return running;
	} // runningProperty()
}
