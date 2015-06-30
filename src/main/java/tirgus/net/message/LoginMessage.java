package tirgus.net.message;

public class LoginMessage extends TirgusMessage
{
    private String login;
    private String encryptedPassword;

    public LoginMessage(String body)
    {
        super(body);

        String parts[] = body.split(",\\s");
        login = parts[0];
        encryptedPassword = parts[1];
    }

    public LoginMessage(String login, String encryptedPassword)
    {
        super(login + ", " + encryptedPassword);
        this.login = login;
        this.encryptedPassword = encryptedPassword;
    }

    public String getLogin()
    {
        return login;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }
}
