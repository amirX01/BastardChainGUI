package org.app.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.app.CLI.Crypt;
import org.app.CLI.User;

public class RegistrationController {
    
    public TextField username;
    public TextField password;
    public static User LogedinUser;
    public void Login(MouseEvent mouseEvent) throws Exception {
        Crypt.initialize();
        User user = User.getUserByUsername(username.getText());
        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed:(");
            alert.setHeaderText("Username not found!");
            alert.showAndWait();
            return;
        }
        if (user.getPassword().equals(password.getText())) {
           System.out.println("login succesfully!");
           LogedinUser = user;
           //MainMenuController mainMenuController = new MainMenuController();
           //MainMenu.setMainMenuController(mainMenuController);
           new MainMenu().start(StartPage.stage);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed:(");
            alert.setHeaderText("Password does not match!");
            alert.showAndWait();
        }
    }

    public void Signup(MouseEvent mouseEvent) throws Exception {
        Crypt.initialize();
        if (User.checkUsernameExists(username.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup failed:(");
            alert.setHeaderText("Username already exists.");
            alert.showAndWait();
            return;
        }
        if (password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup failed:(");
            alert.setHeaderText("Password should not be empty.");
            alert.showAndWait();
            return;
        }
        System.out.println(username.getText());
        System.out.println(password.getText());
        User user = new User(username.getText(), password.getText());
        StartPage.chargeNewAccounts(user.getWallet().publicKey);
        System.out.println(user.getUsername());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Signup Successful:)");
        alert.setHeaderText("You signed up successfully.");
        alert.setContentText("Redirecting to login page.");
        alert.showAndWait();
        System.out.println(243434);
        new LoginPage().start(StartPage.stage);
    }

    public void goToLoginPage(MouseEvent mouseEvent) throws Exception {
        new LoginPage().start(StartPage.stage);
    }

    public void goToSignupPage(MouseEvent mouseEvent) throws Exception {
        new SignupPage().start(StartPage.stage);
    }
}
