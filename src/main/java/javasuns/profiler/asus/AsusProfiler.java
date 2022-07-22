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

package javasuns.profiler.asus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javasuns.profiler.asus.model.PropertyManager;
import javasuns.profiler.asus.model.paneltracker.PanelTracker;


public class AsusProfiler extends Application {

	private String cssFile  = getClass().getResource(PropertyManager.getProjectPath()+"/css/Main.css").toExternalForm();

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane scenePane = new AnchorPane();
		scenePane.getStylesheets().add(cssFile);
		PanelTracker.init(scenePane);
		PanelTracker.getTracker().preLoadPanels();
		PanelTracker.getTracker().showMainScreen();
		primaryStage.setScene(new Scene(scenePane));
		initPrimaryStage(primaryStage);
		primaryStage.show();
	} // start()
	
	
	public static void main(String[] args) {
		launch(args);
	}

	private void initPrimaryStage(Stage primaryStage) {
		primaryStage.setTitle(PropertyManager.getProperty("project.name"));
		primaryStage.getIcons().add(
			new Image(AsusProfiler.class.getResource(PropertyManager.getProjectPath()+"/image/Logo.png").toExternalForm()));
		primaryStage.setOnCloseRequest((t) -> { 
			javafx.application.Platform.exit();
			System.exit(0);
		});
		primaryStage.setWidth(300);
		primaryStage.setHeight(400);
		primaryStage.setMaxHeight(360);
		primaryStage.setMaxWidth(300);
	} // initPrimaryStage
} // class ServicesFX
