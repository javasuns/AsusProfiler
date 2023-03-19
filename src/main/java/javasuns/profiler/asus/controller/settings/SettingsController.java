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

package javasuns.profiler.asus.controller.settings;

import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javasuns.profiler.asus.controller.interfaces.Controller;
import javasuns.profiler.asus.model.PropertyManager;
import javasuns.profiler.asus.model.scheduler.TaskScheduler;


public class SettingsController extends Controller{

	@FXML
	BorderPane rootPane;
	
	@FXML
	Button btnAutoStartup;
	
	@FXML
	HBox hboxAutoStartup;
	
	@FXML
	CheckBox cboxAutoStartup;

	@FXML
	Label lblVersion,lblWebLink;
	
	final static String PROPERTY_AUTOSTART = "settings.autostart";
	
	@FXML
	protected void initialize() {
		lblVersion.setText("v" + PropertyManager.getVersion());
		lblWebLink.setOnMousePressed((e) -> openURL(lblWebLink.getText()));
		initAutoStart();
	} // initialize()
	
	private void initAutoStart() {
		boolean isAutoEnabled = TaskScheduler.getInst().isWindowsTaskActive();
		cboxAutoStartup.setSelected(isAutoEnabled);
	} // initAutoStart()

	@FXML
	private void buttonPressed(ActionEvent event) {
		if(event.getSource() == btnAutoStartup) {
			try {
				if(!cboxAutoStartup.isSelected())
					TaskScheduler.getInst().addWindowsTask();
				else
					TaskScheduler.getInst().deleteWindowsTask();
			} catch (Exception e1) { e1.printStackTrace(); }
			
			cboxAutoStartup.setSelected(TaskScheduler.getInst().isWindowsTaskActive());
		}
	} // buttonPressed
	
	private void openURL(String url) {
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) { e.printStackTrace(); }
	} // openURL()
	
} // class SettingsController
