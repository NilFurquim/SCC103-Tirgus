package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tirgus.model.User;
import tirgus.security.Password;

/**
 * Login window controller
 */
public class Login
{
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    /**
     * Login request
     * @param actionEvent
     */
    public void onLogin(ActionEvent actionEvent)
    {
        final String login = loginField.getText();
        final String password = passwordField.getText();
        User user = null;

        if (!login.isEmpty())
        {
            String encodedSalt = ClientApplication.getMarket().requestSalt(login);
            if (encodedSalt != null)
            {
                String encryptedPassword = Password.encrypt(encodedSalt, password);
                user = ClientApplication.getMarket().sendLogin(login, encryptedPassword);
            }
        }

        if (user != null)
        {
            ClientApplication.getMarket().setCurrentUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("User logged in");
            alert.setHeaderText(user.getName() + " is logged in");
            alert.showAndWait();

            ((Stage) loginField.getScene().getWindow()).close();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Couldn't login");
            alert.setHeaderText("Username and password doesn't match");
            alert.showAndWait();
        }

    }
}
