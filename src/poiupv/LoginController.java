/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import model.User;
import model.Navigation;
import model.NavDAOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;


/**
 * FXML Controller class
 *
 * @author 2005v
 */
public class LoginController implements Initializable {
    
    @FXML private Label loginErrorLabel;
    @FXML private Button loginButton;

    private BooleanProperty validCredentials;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private void checkCredentials() {
    String nick = usernameField.getText();
    String pass = passwordField.getText();

    if (nick.isBlank() || pass.isBlank()) {
        validCredentials.set(false);
        loginErrorLabel.setVisible(false);
        return;
    }

    try {
        User user = Navigation.getInstance().authenticate(nick, pass);
        boolean isValid = user != null;
        validCredentials.set(isValid);
        loginErrorLabel.setVisible(!isValid);
    } catch (NavDAOException e) {
        e.printStackTrace();
        validCredentials.set(false);
        loginErrorLabel.setVisible(true); // Podés poner un mensaje de error general
    }
}

    @FXML
private void handleLogin(ActionEvent event) throws IOException {
    System.out.println("Botón presionado");
    String nick = usernameField.getText();
    String pass = passwordField.getText();

    try {
         User user = Navigation.getInstance().authenticate(nick, pass);
        if (user != null) {
            System.out.println("Login exitoso de: " + user.getNickName());

            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Carta.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual y reemplazar la escena
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    } catch (NavDAOException e) {
        e.printStackTrace();
        System.out.println("Error al autenticar. Verificá la base de datos.");
    }
}



    public void initialize(URL url, ResourceBundle rb) {
        
        validCredentials = new SimpleBooleanProperty(false);
        loginErrorLabel.setVisible(false);

        // Ejecutar una vez para establecer el estado inicial
        checkCredentials();

        // Escuchar cambios en los campos
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> checkCredentials());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> checkCredentials());

        //loginButton.disableProperty().bind(validCredentials.not());
    }    
}
