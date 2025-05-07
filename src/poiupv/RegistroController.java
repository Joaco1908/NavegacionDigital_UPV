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

/**
 * FXML Controller class
 *
 * @author 2005v
 */
public class RegistroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        
    }    
    
private ImageView profileImageView;

private File selectedImageFile;


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

}
