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
package javasuns.profiler.asus.controller.popup;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javasuns.profiler.asus.controller.interfaces.Controller;
import javasuns.profiler.asus.model.ProfileManager;


public class LogViewController extends Controller{

	@FXML
	BorderPane rootPane;
	
	@FXML
	TextArea txtLog;
	
	@FXML
	protected void initialize() {
		headerController.setHeaderText("Log Viewer");
		ProfileManager.getInst().activeProfileProperty().addListener((a,c,profile) -> {
			txtLog.setText(ProfileManager.getInst().getOutput());
		});
	} // initialize()

	public void setText(String text) {
		txtLog.setText(text);
	}

} // class MainScreenController
