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

package javasuns.profiler.asus.model.tray;

import java.net.URL;

import javafx.stage.Stage;

public class TrayIconManager {

	private static TrayIconManager trayIconMngr;
	private TrayIconFX icon;
	
	public static void init(Stage stage, URL imageURL, String appName) {
		trayIconMngr = new TrayIconManager(stage, imageURL, appName);
	}
	
	public static TrayIconManager getInst() {
		if (trayIconMngr == null) {
			System.err.println("TrayIconManager not initialized");
			System.exit(-1);;
		}
		return trayIconMngr;
	} // getInst()

	
	public TrayIconManager(Stage stage, URL iconFile, String application) {
		icon = new TrayIconFX(stage, iconFile, application);
		icon.setMinimizedMessage(application, application + " is running in the background...");
	}
		
	public TrayIconFX getTrayIcon() {
		return icon;
	}
	
} // TrayIconManager
