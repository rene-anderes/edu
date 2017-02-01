package org.anderes.edu.upncalc;

import java.util.ResourceBundle;

import org.anderes.edu.upncalc.guice.CalcGuiceModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalcStarter extends Application {

    private final static Injector INJECTOR = Guice.createInjector(new CalcGuiceModule());
    private Logger logger = LogManager.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Start der Applikation 'UPN-Calc'");
        final CalcController calcController = INJECTOR.getInstance(CalcController.class);
        final ResourceBundle resourceBundle = INJECTOR.getInstance(ResourceBundle.class);

        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource(CalcController.FXMLCALC), resourceBundle);
        myLoader.setController(calcController);
        
        Parent calcScreen = myLoader.load();
        Scene scene = new Scene(calcScreen);
        scene.getStylesheets().add("/fxml/calc.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("UPN Calc");
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

}
