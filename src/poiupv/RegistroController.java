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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
 
/**
 * FXML Controller class
 *
 * @author 2005v
 */
public class RegistroController implements Initializable {
    
    @FXML private ImageView profileImageView;
    @FXML private File selectedImageFile;
    @FXML private Hyperlink loginLink;
    
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
    private void handleRegisterLink() {
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

}
