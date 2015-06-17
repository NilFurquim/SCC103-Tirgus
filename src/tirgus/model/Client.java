package tirgus.model;

import tirgus.security.Password;

public class Client
{
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String login;
    private Password password;

    public Client(String name, String address, String telephone, String email, String login, Password password)
    {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLogin()
    {
        return login;
    }

    public Password getPassword()
    {
        return password;
    }
}
