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

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javasuns.profiler.asus.model.PropertyManager;

public class TrayIconPopup extends Popup {
	private Pane rootPane;

	private Timeline timelineHide, timelineShow;

	private Stage stage;

	public TrayIconPopup() {
		initStage();
		this.setAutoFix(true);
	} // initialize()
	
	public void setPane(Pane popupPane) {
		rootPane = popupPane;
		this.getContent().clear();
		this.getContent().add(popupPane);
		initHideTimeline();
		initShowTimeline();
	}
	
	private void initStage() {
		AnchorPane scenePane = new AnchorPane();
		scenePane.getStylesheets().add(getClass().getResource(PropertyManager.getProjectPath() + "/css/Main.css")
				.toExternalForm());
		stage = new Stage();
		stage.setScene(new Scene(scenePane));
		stage.initStyle(StageStyle.UTILITY);
	    stage.setMaxHeight(0);
	    stage.setMaxWidth(0);
	    stage.setWidth(0);
	    stage.setHeight(0);
	    stage.setX(Double.MAX_VALUE);
		stage.show();
		
		
		stage.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
			if(!isFocused && this.isShowing())
				timelineHide.play();
		});
		
	}

	public void showPopup(int x, int y) {
		Platform.runLater(() -> {
			this.show(stage, x, y);
			timelineShow.play();
			stage.requestFocus();
		});
	}
	
	public void hide() {
		Platform.runLater(() -> {
			super.hide();
		});
	}

	private void initHideTimeline() {
		KeyValue fadeOutBegin = new KeyValue(rootPane.opacityProperty(), 1.0);
		KeyValue fadeOutEnd = new KeyValue(rootPane.opacityProperty(), 0.0);
		KeyFrame kfBegin = new KeyFrame(Duration.ZERO, new KeyValue[] { fadeOutBegin });
		KeyFrame kfEnd = new KeyFrame(Duration.millis((double) 250.0), new KeyValue[] { fadeOutEnd });
		timelineHide = new Timeline(new KeyFrame[] { kfBegin, kfEnd });
		timelineHide.setOnFinished((e) -> rootPane.setManaged(false));
	} // initHideTimeline()

	private void initShowTimeline() {
		KeyValue setVisible = new KeyValue(rootPane.managedProperty(), true);
		KeyValue fadeOutBegin = new KeyValue(rootPane.opacityProperty(), 0.0);
		KeyValue fadeOutEnd = new KeyValue(rootPane.opacityProperty(), 1.0, Interpolator.EASE_IN);
		KeyFrame kfBegin = new KeyFrame(Duration.ZERO, new KeyValue[] { setVisible, fadeOutBegin });
		KeyFrame kfEnd = new KeyFrame(Duration.millis((double) 250.0), new KeyValue[] { fadeOutEnd });
		timelineShow = new Timeline(new KeyFrame[] { kfBegin, kfEnd });
	} // initShowTimeline()
}
