package org.minisim;

import static org.minisim.AppConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.minisim.simulation.Simulation;
import org.minisim.simulation.force.Friction;
import org.minisim.simulation.force.Gravity;
import org.minisim.view.BottomBar;
import org.minisim.view.SimulationView;
import org.minisim.view.TopBar;

public class App extends Application {

    public static Logger logger = null;

    static {
        InputStream stream = App.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger("minisim");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Simulation sim = Simulation.builder()
            .nBodies(1000)
            .width(500)
            .height(500)
            // .addForce(new Gravity(-1e-4))
            .addForce(new Gravity(1e-3))
            // .addForce(new GravityDown(0.1))
            .addForce(new Friction(0.99))
            .solidBorders()
            .build();

    public static Simulation getSimulation() {
        return sim;
    }

    @Override
    public void start(final Stage stage) {
        BorderPane bPane = new BorderPane();

        bPane.setTop(new TopBar());
        bPane.setBottom(new BottomBar());
        bPane.setCenter(new SimulationView());

        final Scene scene = new Scene(bPane);

        stage.setTitle("MiniSim");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        logger.info("MiniSim is running on:");
        logger.info(" - " + OSName + " " + OSVersion);
        logger.info(" - Java " + javaVersion);
        logger.info(" - JVM " + jvmVersion);
        logger.info(" - JavaFX " + javafxVersion);
        launch(args);
    }
}