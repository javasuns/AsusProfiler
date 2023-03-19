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

package javasuns.profiler.asus.controller.header;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javasuns.profiler.asus.controller.interfaces.Controller;
import javasuns.profiler.asus.model.paneltracker.PanelTracker;

public class HeaderController extends Controller {

	@FXML
	Label lblTitle;
	
	@FXML
	Button btnSettings, btnBack;

	public void setHeaderText(String text) {
		lblTitle.setText(text);
	} // setHeaderText()
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource() == btnBack)
			PanelTracker.getTracker().getActiveTabController().goToPreviousPane();
		else if (event.getSource() == btnSettings)
			PanelTracker.getTracker().getActiveTabController().setPane(PanelTracker.getTracker().getSettingsPane());
	} // handleButtonAction

} // class HeaderController
