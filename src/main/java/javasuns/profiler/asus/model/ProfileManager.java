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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProfileManager {
	private static ProfileManager profileMngr;
	private ProfileLoader profileLoader;
	private Profile loadingProfile;
	private ObjectProperty<Profile> activeProfileProperty = new SimpleObjectProperty<>();

	public static ProfileManager getInst() {
		if (profileMngr == null)
			profileMngr = new ProfileManager();
		return profileMngr;
	} // getInst()

	public ProfileManager() {
		profileLoader = new ProfileLoader();
		activeProfileProperty.set(
			switch(PropertyManager.getProperty("profile.active").toLowerCase()) {
				case "ultra" -> Profile.ULTRA;
				case "silent" -> Profile.SILENT;
				case "balanced" -> Profile.BALANCED;
				case "performance" -> Profile.PERFORMANCE;
				default -> Profile.BALANCED;
		});
		
		profileLoader.runningProperty().addListener((a,b,running) -> {
			if(!running) {
				activeProfileProperty.set(loadingProfile);
			}
		});
	} // Constructor Method

	public Profile getActiveProfile() {
		return activeProfileProperty.get();
	} // getActiiveProfile
	
	public Profile getLoadingProfile() {
		return loadingProfile;
	}
	
	public void setActiveProfile(Profile profile) {
		loadingProfile = profile;
		profileLoader.loadProfile(profile);
	}

	public static enum Profile {
		ULTRA("profile.ultra"),
		SILENT("profile.silent"),
		BALANCED("profile.balanced"),
		PERFORMANCE("profile.performance");
		
		private String property;

		Profile(String property) {
			this.property = property;
		}
		
		public String toString() { return property; }
	} // Profile
	
	public BooleanProperty loadingProfileProperty() {
		return profileLoader.runningProperty();
	} // loadingProfileProperty() 
	
	public ObjectProperty<Profile> activeProfileProperty() {
		return activeProfileProperty;
	}
	
	public String getOutput() {
		return profileLoader.getOutput();
	}
	
}
