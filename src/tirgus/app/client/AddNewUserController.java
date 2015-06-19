package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tirgus.model.Market;
import tirgus.model.User;
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

        //TODO alert

        if(!(name.isEmpty() && login.isEmpty() && passField.getText().isEmpty()))
        {
            Market.instance().newUser(new User(name, address, tel, email, login, password));
            ((Stage)loginField.getScene().getWindow()).close();
        }

    }
}
