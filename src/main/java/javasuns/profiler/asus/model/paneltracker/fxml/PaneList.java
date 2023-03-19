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

package javasuns.profiler.asus.model.paneltracker.fxml;

import java.io.IOException;
import java.util.HashMap;

import javafx.scene.layout.Pane;
import javasuns.profiler.asus.controller.interfaces.Controller;
import javasuns.profiler.asus.model.PropertyManager;

public class PaneList {

	HashMap<PaneFXML, Pane> oldPanes = new HashMap<PaneFXML, Pane>();
	HashMap<Pane, Controller> panesController = new HashMap<Pane, Controller>();
	HashMap<PaneFXML, FXMLPane<Controller>> panes = new HashMap<>();


	private static final String mainPath = PropertyManager.getProjectPath() + "/view";

	public static enum PaneFXML {
		MAIN("/MainScreen.fxml"), 
		SETTINGS("/settings/Settings.fxml"),
		TAB("/tabs/Tab.fxml"),
		LOG_VIEW("/popup/LogView.fxml"),
		TRAY_ICON_POPUP("/popup/TrayIconPopup.fxml");

		private final String url;

		private PaneFXML(String url) {
			this.url = mainPath + url;
		}
	};

	private FXMLPane<Controller> initPane(PaneFXML paneFXML) {
		try {
			var fxmlP = new FXMLPane<Controller>(paneFXML.url);
			panes.put(paneFXML, fxmlP);
			return fxmlP;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} // initPane()

	public synchronized Pane getPane(PaneFXML id) {
		FXMLPane<Controller> fxmlPane = panes.get(id);
		if(fxmlPane == null )
			fxmlPane = initPane(id);

		return fxmlPane.getPane();
	} // getPane()

	@SuppressWarnings("unchecked")
	public <T> T getController(PaneFXML id) {
		return (T) panes.get(id).getController();
	} // getController()

} // class PanelList
