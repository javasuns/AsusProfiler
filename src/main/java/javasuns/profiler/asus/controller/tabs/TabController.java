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

package javasuns.profiler.asus.controller.tabs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javasuns.profiler.asus.controller.interfaces.Controller;

public class TabController extends Controller{

	@FXML
	AnchorPane tabPane;

	public void setPane(Parent pane) {
		Platform.runLater(() -> tabPane.getChildren().add(pane));
	} // setPane()
	
	public void goToPreviousPane() { 
		if(tabPane.getChildren().size()>1)
			Platform.runLater(() -> tabPane.getChildren().remove(tabPane.getChildren().size()-1));
	}

} // class TabController
