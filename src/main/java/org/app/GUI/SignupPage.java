package org.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.CLI.*;

import java.net.URL;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class SignupPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Graphic
        StartPage.stage = stage;
        Pane SignupPane = FXMLLoader.load(new URL(SignupPage.class.getResource("/fxml/SignupMenu.fxml").toExternalForm()));
        Scene scene = new Scene(SignupPane);
        stage.setTitle("Shaeer is alive");
        stage.setScene(scene);
        stage.show();
    }
}
