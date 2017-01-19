package org.anderes.edu.di;

import java.util.ResourceBundle;

import org.anderes.edu.di.guice.CalcGuiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalcStarter extends Application {

    private final static Injector INJECTOR = Guice.createInjector(new CalcGuiceModule());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        final CalcController calcController = INJECTOR.getInstance(CalcController.class);
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("CalcLanguagePack");

        final FXMLLoader myLoader = new FXMLLoader(getClass().getResource(CalcController.FXMLCALC), resourceBundle);
        myLoader.setController(calcController);
        
        Parent calcScreen = myLoader.load();
        Scene scene = new Scene(calcScreen);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UPN Calc");
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

}
