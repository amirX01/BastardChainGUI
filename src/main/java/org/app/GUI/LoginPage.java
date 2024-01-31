package org.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.CLI.User;

import java.net.URL;

public class LoginPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane LoginPane = FXMLLoader.load(new URL(SignupPage.class.getResource("/fxml/LoginMenu.fxml").toExternalForm()));
        Scene scene = new Scene(LoginPane);
        stage.setTitle("Shaeer is alive");
        stage.setScene(scene);
        //stage.show();
    }
}
