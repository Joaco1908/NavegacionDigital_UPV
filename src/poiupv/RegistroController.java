/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Navigation;
import model.User;
 
/**
 * FXML Controller class
 *
 * @author 2005v
 */
public class RegistroController implements Initializable {
    
    @FXML private ImageView profileImageView;
    @FXML private File selectedImageFile;
    @FXML private Hyperlink loginLink;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker birthDatePicker;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      


        
    }    
    private void handleUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona tu foto de perfil");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            selectedImageFile = file;
            Image image = new Image(file.toURI().toString());
            profileImageView.setImage(image);
        }
    }

    @FXML
    private void handleLoginLink() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginLink.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRegister(){
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        LocalDate birthDate = birthDatePicker.getValue();
        
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || birthDate == null){
        System.out.println("Todos los campos son obligatorios.");
        return;
        }
        
        if (!password.equals(confirmPassword)) {
        System.out.println("Las contraseñas no coinciden.");
        return;
        }
        
        if (!User.checkNickName(username)) {
        System.out.println("El nombre de usuario no es válido.");
        return;
        }
        
        if (!User.checkEmail(email)) {
        System.out.println("El correo electrónico no es válido.");
        return;
        }

        if (!User.checkPassword(password)) {
            System.out.println("La contraseña no cumple con los requisitos.");
            return;
        }

        if (birthDate.isAfter(LocalDate.now().minusYears(16))) {
            System.out.println("Debes tener al menos 16 años.");
            return;
        }

        System.out.println("Validación exitosa. Listo para registrar.");
        
        try{
            Image avatar;
            if(selectedImageFile != null){
                avatar = new Image(new FileInputStream(selectedImageFile));
            } 
            else{
                avatar = new Image(getClass().getResourceAsStream("/resources/user-solid.png"));
            }
            
            Navigation nav = Navigation.getInstance();
            
            if(nav.exitsNickName(username)){
                System.out.println("Ya existe un usuario con ese nombre.");
                return;
            }
            
            //Volver a la página de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            User newUser = nav.registerUser(username, email, password, avatar, birthDate);
            
            System.out.println("Usuario registrado con éxito: " + newUser.getNickName());
            
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error al registrar al usuario");
        }
    }
    @FXML
private void handleUploadPhoto(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Selecciona tu foto de perfil");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
    );

    File file = fileChooser.showOpenDialog(null);

    if (file != null) {
        selectedImageFile = file;
        Image image = new Image(file.toURI().toString());
        profileImageView.setImage(image);
      
        double radius = Math.min(profileImageView.getFitWidth(), profileImageView.getFitHeight()) / 2;

        Circle clip = new Circle(radius, radius, radius);
        profileImageView.setClip(clip);
        
        Circle border = new Circle(25);
        border.setStroke(Color.BLACK);
        border.setFill(Color.TRANSPARENT);
        border.setStrokeWidth(2);
    }
}

}
