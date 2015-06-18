package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tirgus.model.Client;
import tirgus.security.Password;

public class AddNewUserController
{
    @FXML
    TextField nameField;

    @FXML
    TextField addressField;

    @FXML
    TextField telField;

    @FXML
    TextField emailField;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passField;

    public void onRegister(ActionEvent actionEvent)
    {
        final String name = nameField.getText();
        final String address = addressField.getText();
        final String tel = telField.getText();
        final String email = emailField.getText();
        final String login = loginField.getText();
        final Password password = new Password(passField.getText());

        if(!(name.isEmpty() && login.isEmpty() && passField.getText().isEmpty()))
        {
            new Client(name, address, tel, email, login, password);
            ((Stage)loginField.getScene().getWindow()).close();
        }
    }
}
