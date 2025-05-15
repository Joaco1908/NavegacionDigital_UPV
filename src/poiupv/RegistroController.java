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
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
    @FXML private VBox registerVBox;
    @FXML private VBox leftContentBox;
    @FXML private VBox formBox;
    @FXML private Label userError;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Label passwordConfirmError;
    @FXML private Label dateError;
    @FXML private Button registerButton;

    private BooleanProperty validUsername;
    private BooleanProperty validEmail;
    private BooleanProperty validPassword;
    private BooleanProperty confirmPasswords;
    private BooleanProperty validBirthDate;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        
        registerVBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                bindScaling(newScene);
            }
        });

        validUsername = new SimpleBooleanProperty(false);
        validEmail = new SimpleBooleanProperty(false);
        validPassword = new SimpleBooleanProperty(false);
        confirmPasswords = new SimpleBooleanProperty(false);
        validBirthDate = new SimpleBooleanProperty(false);
        
        // Listeners
        usernameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateUsername();
        });

        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateEmail();
        });

        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validatePassword();
        });

        confirmPasswordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateConfirmPassword();
        });

        birthDatePicker.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateBirthDate();
        });
        
        BooleanBinding validFields = validUsername
        .and(validEmail)
        .and(validPassword)
        .and(confirmPasswords)
        .and(validBirthDate);

        registerButton.disableProperty().bind(validFields.not());
    }
    
    private void showError(boolean isValid, Node field, Node errorMessage){
        errorMessage.setVisible(!isValid);
        field.setStyle(((isValid) ? "" : "-fx-background-color: #FCE5E0"));
    }
  

    private void bindScaling(Scene scene) {
        double baseWidth = 900;
        double baseHeight = 600;
        double maxScale = 1.3;

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scale = Math.min(newVal.doubleValue() / baseWidth, maxScale);
            registerVBox.setScaleX(scale);
            leftContentBox.setScaleX(scale);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double scale = Math.min(newVal.doubleValue() / baseHeight, maxScale);
            registerVBox.setScaleY(scale);
            registerVBox.setSpacing(18 * scale);

            leftContentBox.setScaleY(scale);
            leftContentBox.setSpacing(20 * scale);
        });
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
    private void handleRegister() {
        validateUsername();
        validateEmail();
        validatePassword();
        validateConfirmPassword();
        validateBirthDate();

        if (!validUsername.get() || !validEmail.get() || !validPassword.get()
                || !confirmPasswords.get() || !validBirthDate.get()) {
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        LocalDate birthDate = birthDatePicker.getValue();

        try {
            Image avatar;
            if (selectedImageFile != null) {
                avatar = new Image(new FileInputStream(selectedImageFile));
            } else {
                avatar = new Image(getClass().getResourceAsStream("/resources/user-solid.png"));
            }

            Navigation nav = Navigation.getInstance();

            if (nav.exitsNickName(username)) {
                userError.setText("Ese nombre de usuario ya está en uso");
                showError(false, usernameField, userError);
                return;
            }

            User newUser = nav.registerUser(username, email, password, avatar, birthDate);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
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
    private void validateUsername() {
        boolean isValid = User.checkNickName(usernameField.getText().trim());
        validUsername.set(isValid);
        showError(isValid, usernameField, userError);
    }
    
    private void validateEmail() {
        boolean isValid = User.checkEmail(emailField.getText().trim());
        validEmail.set(isValid);
        showError(isValid, emailField, emailError);
    }
    private void validatePassword() {
        boolean isValid = User.checkPassword(passwordField.getText().trim());
        validPassword.set(isValid);
        showError(isValid, passwordField, passwordError);
    }
    
    private void validateConfirmPassword() {
        boolean match = passwordField.getText().equals(confirmPasswordField.getText());
        confirmPasswords.set(match);
        showError(match, confirmPasswordField, passwordConfirmError);
    }
    
    private void validateBirthDate() {
        LocalDate birthDate = birthDatePicker.getValue();
        boolean isValid = birthDate != null && birthDate.isBefore(LocalDate.now().minusYears(16));
        validBirthDate.set(isValid);
        showError(isValid, birthDatePicker, dateError);
    }






}