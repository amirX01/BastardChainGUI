package org.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.CLI.BastardChain;
import org.app.CLI.Block;
import org.app.CLI.Crypt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;

public class TransactionPage extends Application {
    public Label assests;

    public void goToMainMenu(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(StartPage.stage);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane LoginPane = FXMLLoader.load(new URL(SignupPage.class.getResource("/fxml/TransactionPage.fxml").toExternalForm()));
        Scene scene = new Scene(LoginPane);
        stage.setTitle("Shaeer is alive");
        stage.setScene(scene);
    }
    public void initialize() {
        assests.setText("Max: " + Float.toString(RegistrationController.LogedinUser.getWallet().getBalance()));
    }

    public void preformTransaction(MouseEvent mouseEvent) {

    }
    public static void MakeTransaction(PublicKey to) {
        Crypt.initialize();
        Block block1 = new Block(BastardChain.blockchain.getLast().hash);
        System.out.println("\nWalletA's balance is: " + BastardChain.walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to NewWallet...");
        block1.addTransaction(RegistrationController.LogedinUser.getWallet().sendFunds(to, 40f));
        BastardChain.addBlock(block1);
    }
}
