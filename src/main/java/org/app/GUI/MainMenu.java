package org.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.CLI.Crypt;

import java.net.URL;

public class MainMenu extends Application {
    //public static MainMenuController mainMenuController;

    public Label assests;

    //public void setLabelBalanceAmount(){
        //Crypt.initialize();
        //assessts.requestFocus();
        //assessts.setText(Float.toString(RegistrationController.LogedinUser.getWallet().getBalance()));
        //assests.setText("kir");
    //}
    public void goToLoginMenu(MouseEvent mouseEvent) throws Exception {
        new LoginPage().start(StartPage.stage);
    }

    public void goToTransactionPage(MouseEvent mouseEvent) throws Exception {
        new TransactionPage().start(StartPage.stage);
    }
    @Override
    public void start(Stage stage) throws Exception{
        Crypt.initialize();
        Pane LoginPane = FXMLLoader.load(new URL(SignupPage.class.getResource("/fxml/MainMenu.fxml").toExternalForm()));
        //mainMenuController.setLabelBalanceAmount();
        //mainMenuController.setLabelBalanceAmount();
        Scene scene = new Scene(LoginPane);
        stage.setTitle("Shaeer is alive");
        stage.setScene(scene);
        stage.show();
    }
    //public static void setMainMenuController(MainMenuController controller){
        //mainMenuController = controller;
    //}
    @FXML
    public void initialize() {
        assests.setText("~" + Float.toString(RegistrationController.LogedinUser.getWallet().getBalance()));
    }
}
