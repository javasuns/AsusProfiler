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

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.stage.Stage;

public class TrayIconFX {
	private SystemTray tray;
	private Image image;
	private TrayIcon trayIcon;
	private String messageTitle;
	private String minimizedMessage;
	private boolean minimizedMessageShown = true;
	private Stage primaryStage;
	private TrayIconPopup popup;

	public TrayIconFX(Stage primaryStage, URL iconFile, String appDescription) {
		if (SystemTray.isSupported()) {
			this.primaryStage = primaryStage;
			tray = SystemTray.getSystemTray();
			image = Toolkit.getDefaultToolkit().getImage(iconFile);
			trayIcon = new TrayIcon(image, appDescription);
			trayIcon.setImageAutoSize(true);
			popup = new TrayIconPopup();
			initPrimaryStage();
		} // if
	}
	
	private void initPrimaryStage() {
		Platform.setImplicitExit(false);
		primaryStage.setIconified(false);
		primaryStage.iconifiedProperty().addListener((ov, t, minimized) -> {
			if (minimized) {
				showTrayHideStage();
			}
		});
		
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if(e.getClickCount() >= 2) {
						hide();
						Platform.runLater(() -> {
							restoreStageSize();
							primaryStage.setIconified(false);
							primaryStage.show();
						});
					} // if
					else
						popup.showPopup(e.getXOnScreen(), e.getYOnScreen());
				}
				else if (e.getButton() == MouseEvent.BUTTON3)
					popup.showPopup(e.getXOnScreen(), e.getYOnScreen());				
			} // mousePressed
		});
		
		primaryStage.setOnCloseRequest((e) -> {
			Platform.exit();
			System.exit(0);
		});
	}

	public void setMinimizedMessage(String title, String message) {
		this.messageTitle = title;
		this.minimizedMessage = message;
	}

	public void showMinimizedMessage() {
		if (minimizedMessageShown) {
			showMessage(messageTitle, minimizedMessage);
			minimizedMessageShown = false;
		}
	}

	public void showMessage(String title, String message) {
		trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
	}

	public boolean isMinimizedToTray() {
		return tray.getTrayIcons().length > 0;
	}
	
	public void setIcon(String iconPath) {
		trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(iconPath)));
	}
	
	private void show() {
		try {
			if(tray.getTrayIcons().length == 0)
				tray.add(trayIcon);
		} catch(AWTException e) {
			System.err.println("TrayIcon could not be added.");
			e.printStackTrace();
		}
	}
	
	private void hide() {
		popup.hide();
		tray.remove(trayIcon);
	}
	
	public TrayIconPopup getPopupMenu() {
		return popup;
	}
	
	private Dimension2D stageSizeBeforeMinimized;
	
	private void saveStageSize() {
		stageSizeBeforeMinimized = new Dimension2D(primaryStage.getWidth(), primaryStage.getHeight());
	}
	
	private void restoreStageSize() {
		primaryStage.setWidth(stageSizeBeforeMinimized.getWidth());
		primaryStage.setHeight(stageSizeBeforeMinimized.getHeight());
	}
	
	public void showTrayHideStage() {
		saveStageSize();
		Platform.runLater(() -> primaryStage.hide());
		show();
	}
}
