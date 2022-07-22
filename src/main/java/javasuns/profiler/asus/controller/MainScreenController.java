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

package javasuns.profiler.asus.controller;

import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javasuns.profiler.asus.controller.interfaces.Controller;
import javasuns.profiler.asus.model.ProfileManager;
import javasuns.profiler.asus.model.PropertyManager;
import javasuns.profiler.asus.model.ProfileManager.Profile;
import javasuns.profiler.asus.model.paneltracker.PanelTracker;


public class MainScreenController extends Controller{

	@FXML
	BorderPane rootPane;
	
	@FXML
	Button btnUltra, btnSilent, btnBalanced, btnPerformance;
	
	@FXML
	Region rgnActivatedUltra, rgnActivatedSilent, rgnActivatedBalanced, rgnActivatedPerformance;
	
	@FXML
	HBox hboxUltra, hboxSilent, hboxBalanced, hboxPerformance;
	
	ProgressIndicator prgLoading = new ProgressIndicator(-1);

	Map<Profile, Region> regions;
	Map<Profile, HBox> hboxes;
	@FXML
	protected void initialize() {
		System.err.println(headerController);
				System.err.println(PropertyManager.getProperty("project.name"));

		headerController.setHeaderText(PropertyManager.getProperty("project.name"));
		regions = Map.of(
				Profile.ULTRA,rgnActivatedUltra,
				Profile.SILENT,rgnActivatedSilent,
				Profile.BALANCED,rgnActivatedBalanced,
				Profile.PERFORMANCE,rgnActivatedPerformance);		
		
		hboxes = Map.of(
				Profile.ULTRA,hboxUltra,
				Profile.SILENT,hboxSilent,
				Profile.BALANCED,hboxBalanced,
				Profile.PERFORMANCE,hboxPerformance);		
		
		regions.entrySet().stream().forEach(entry -> entry.getValue().setVisible(false));
		regions.get(ProfileManager.getInst().getActiveProfile()).setVisible(true);
		
		ProfileManager.getInst().loadingProfileProperty().addListener((a,b,loadingProfile) -> {
			Platform.runLater(()-> {
				if(loadingProfile)
					hboxes.get(ProfileManager.getInst().getLoadingProfile()).getChildren().add(prgLoading);
				else
					hboxes.get(ProfileManager.getInst().getLoadingProfile()).getChildren().remove(prgLoading);
			});
		});
		
		ProfileManager.getInst().activeProfileProperty().addListener((a,prevProfile,profile) -> {
			Platform.runLater(()-> {
				regions.get(prevProfile).setVisible(false);
				regions.get(profile).setVisible(true);
			});
		});
	} // initialize()

	@FXML
	private void buttonPressed(ActionEvent event) {
		Profile profile = event.getSource() == btnUltra ? Profile.ULTRA
						: event.getSource() == btnSilent ?  Profile.SILENT
						: event.getSource() == btnBalanced ? Profile.BALANCED
						: event.getSource() == btnPerformance ? Profile.PERFORMANCE
						: null;
		
		System.err.println("Activating " + profile);
		ProfileManager.getInst().setActiveProfile(profile);

	} // buttonPressed
	
	@FXML
	private void buttonLogViewerPressed() {
		PanelTracker.getTracker().showLogView();
	}
	

} // class MainScreenController
