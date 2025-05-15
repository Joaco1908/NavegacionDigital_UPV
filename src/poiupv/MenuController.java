/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Navigation;
import model.User;

public class MenuController {

    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;

    private ContextMenu profileMenu;
    @FXML
    private Button cartaButton;

    public void initialize() {
        // Asignar imagen de perfil (por defecto si no hay personalizada)
        Image defaultProfile = new Image(getClass().getResourceAsStream("/resources/profile-solid.png"));
        profileImage.setImage(defaultProfile); // Sustituir por la imagen del usuario si está disponible

        // Menú contextual
        profileMenu = new ContextMenu();

        MenuItem editProfile = new MenuItem("Modificar perfil");
        editProfile.setOnAction(e -> handleEditProfile());

        MenuItem logout = new MenuItem("Cerrar sesión");
        logout.setOnAction(e -> handleLogout());

        profileMenu.getItems().addAll(editProfile, logout);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        profileMenu.show(profileImage, event.getScreenX(), event.getScreenY());
    }

    // Los métodos para las acciones
    private void handleEditProfile() {
            System.out.println("Editar perfil");
    }
    
    @FXML
    private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cerrar sesión");
        confirm.setHeaderText("¿Seguro que deseas cerrar sesión?");
        confirm.setContentText("Perderás el progreso no guardado.");

        ButtonType yes = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(yes, no);

        confirm.showAndWait().ifPresent(response -> {
            if (response == yes) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                    Parent root = loader.load();
                    Stage currentStage = (Stage) profileImage.getScene().getWindow();
                    currentStage.setScene(new Scene(root));
                    currentStage.setTitle("Iniciar sesión");
                    currentStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @FXML private void handleSelectExercise() {
        System.out.println("Seleccionar ejercicio");
    }

    @FXML private void handleRandomExercise() {
        System.out.println("Ejercicio aleatorio");
    }
                                
    @FXML private void handleCartaNautica() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("carta.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) cartaButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
    }
    
    
}
