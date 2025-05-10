package poiupv;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Navigation;
import model.User;
import model.NavDAOException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginErrorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginErrorLabel.setVisible(false);
    }

    @FXML
    private void handleLogin() {
        String nick = usernameField.getText();
        String pass = passwordField.getText();

        try {
            User user = Navigation.getInstance().authenticate(nick, pass);
            if (user != null) {
                System.out.println("Login exitoso de: " + user.getNickName());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Carta.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                loginErrorLabel.setVisible(true);
            }
        } catch (NavDAOException | IOException e) {
            e.printStackTrace();
            loginErrorLabel.setVisible(true);
        }
    }

    @FXML
    private void handleRegisterLink() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
