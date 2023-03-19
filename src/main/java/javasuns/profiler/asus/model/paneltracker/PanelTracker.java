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

package javasuns.profiler.asus.model.paneltracker;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javasuns.profiler.asus.controller.tabs.TabController;
import javasuns.profiler.asus.model.PropertyManager;
import javasuns.profiler.asus.model.paneltracker.fxml.PaneList;
import javasuns.profiler.asus.model.paneltracker.fxml.PaneList.PaneFXML;

public class PanelTracker {
   private static PanelTracker panelTracker;
   
   private PaneList panelList;
   private AnchorPane scenePane;
   private Pane activeTab;

private Stage popupWindow;
   
   public static PanelTracker init(AnchorPane scenePane) {
      panelTracker = new PanelTracker(scenePane);
      return panelTracker;
   } // init()

   public static PanelTracker getTracker() {
	if(panelTracker == null) {
	   System.err.println("PanelTracker not initialized!");
	   System.exit(-100);
	} // if
      	return panelTracker;
   } // getTracker()

   private PanelTracker(AnchorPane scenePane) {
   	this.scenePane = scenePane; 
   } // Constructor Method

   // Preloading FXML Panes for better user experience
   public void preLoadPanels() {
   		panelList = new PaneList();
        getMainScreenPane();
        getLogViewer();
        panelList.getPane(PaneFXML.TAB);
   } // initBasicPanels()
   
   public void addToScenePane(Parent pane) {
   	Platform.runLater(() -> {
           scenePane.getChildren().add(0,pane);
           AnchorPane.setTopAnchor(pane, 0.0);
           AnchorPane.setBottomAnchor(pane, 0.0);
           AnchorPane.setLeftAnchor(pane, 0.0);
           AnchorPane.setRightAnchor(pane, 0.0);
           scenePane.layout();
           scenePane.applyCss();
       });
   } // addToScenePane()
   
   public TabController getActiveTabController() {
   	return panelList.getController(PaneFXML.TAB);
   } // getActiveTabController()
   
   public void showMainScreen() {
	activeTab = panelList.getPane(PaneFXML.TAB);
	activeTab.getChildren().add(getMainScreenPane());
	addToScenePane(activeTab);
   } // goToLoginPane()
	
   public Parent getMainScreenPane() { return panelList.getPane(PaneFXML.MAIN); }
   public Parent getSettingsPane() { return panelList.getPane(PaneFXML.SETTINGS); }
   public Pane getTrayIconPopupPane() { return panelList.getPane(PaneFXML.TRAY_ICON_POPUP); }
   private Parent getLogViewer() { return panelList.getPane(PaneFXML.LOG_VIEW); }
   
   public void showLogView() {
	   getLogViewerWindow().show();
   }
   
	public Stage getLogViewerWindow() {
		if (popupWindow == null) {
			popupWindow = new Stage();
			popupWindow.initModality(Modality.APPLICATION_MODAL);
			// popupWindow.initStyle(StageStyle.UNDECORATED);
			String cssFile = getClass().getResource(PropertyManager.getProjectPath() + "/css/Main.css").toExternalForm();
			getLogViewer().getStylesheets().add(cssFile);
			popupWindow.getIcons().add(
					new Image(this.getClass().getResource(PropertyManager.getProjectPath()+"/image/Logo.png").toExternalForm()));
			popupWindow.setScene(new Scene(getLogViewer()));
			popupWindow.setTitle("Log Viewer");
		}

		return popupWindow;
	}
} // class PanelTracker
