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
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField telField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;

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
