package org.anderes.edu.upncalc;

import java.util.ResourceBundle;

import org.anderes.edu.upncalc.guice.CalcGuiceModule;
import static org.apache.commons.lang3.StringUtils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CalcStarter extends Application {

    public final static String BASIS_URL = "/" + replace(CalcStarter.class.getPackage().getName(), ".", "/"); 
    public final static String FXMLCALC = BASIS_URL + "/fxml/JavaFX-Calc.fxml";
    private final static String CSS_PATH = BASIS_URL + "/fxml/calc.css";
    private final static String CALCULATOR_IMG = BASIS_URL + "/images/calculator.png";
    private final Injector INJECTOR = Guice.createInjector(new CalcGuiceModule());
    private Logger logger = LogManager.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        logger.info("Stop der Applikation 'UPN-Calc'");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Start der Applikation 'UPN-Calc'");
        final CalcController calcController = INJECTOR.getInstance(CalcController.class);
        final ResourceBundle resourceBundle = INJECTOR.getInstance(ResourceBundle.class);
        
        logger.trace("Load FXML-Calc: " + FXMLCALC);
        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource(FXMLCALC), resourceBundle);
        myLoader.setController(calcController);
        
        final Parent calcScreen = myLoader.load();
        final Scene scene = new Scene(calcScreen);
        scene.getStylesheets().add(CSS_PATH);
        primaryStage.getIcons().add(new Image(CALCULATOR_IMG));
        primaryStage.setScene(scene);
        primaryStage.setTitle(resourceBundle.getString("window.title"));
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

}
