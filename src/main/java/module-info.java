module javasuns.profiler.asus {
	requires javafx.fxml;
	requires javafx.controls;
	requires transitive javafx.graphics;
	
	// Required to start a JavaFX Application
	opens   javasuns.profiler.asus to javafx.graphics;
	exports javasuns.profiler.asus to javafx.graphics;
	
	// FXML Resources
	exports javasuns.profiler.asus.controller to javafx.fxml;
	opens   javasuns.profiler.asus.controller to javafx.fxml;
	
	exports javasuns.profiler.asus.controller.tabs to javafx.fxml;
	opens   javasuns.profiler.asus.controller.tabs to javafx.fxml;
	
	exports javasuns.profiler.asus.controller.header to javafx.fxml;
	opens   javasuns.profiler.asus.controller.header to javafx.fxml;
	
	exports javasuns.profiler.asus.controller.popup to javafx.fxml;
	opens   javasuns.profiler.asus.controller.popup to javafx.fxml;
	
	exports javasuns.profiler.asus.controller.interfaces to javafx.fxml;
	opens   javasuns.profiler.asus.controller.interfaces to javafx.fxml;
	
	exports javasuns.profiler.asus.model.paneltracker.fxml to javafx.fxml;
	opens   javasuns.profiler.asus.model.paneltracker.fxml to javafx.fxml;
}