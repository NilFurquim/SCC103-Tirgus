package tirgus.model;

import tirgus.security.Password;
import tirgus.serialization.OrderedSerializable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * User model
 */
public class User extends OrderedSerializable
{
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String login;
    private Password password;

    /**
     * Constructor
     */
    public User()
    {

    }

    /**
     * Constructor
     */
    public User(String name, String address, String telephone, String email, String login, Password password)
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

    /**
     * Serialize object
     * @return
     */
    @Override
    protected List<String> customOutputData()
    {
        return Arrays.asList(
                getName(),
                getAddress(),
                getTelephone(),
                getEmail(),
                getLogin(),
                getPassword().getEncodedSalt(),
                getPassword().getEncryptedPassword()
        );
    }

    /**
     * Read object values
     * @param itr
     */
    @Override
    public void customInputData(Iterator<String> itr)
    {
        name = itr.next();
        address = itr.next();
        telephone = itr.next();
        email = itr.next();
        login = itr.next();
        password = new Password(itr.next(), itr.next(), true);
    }
}
