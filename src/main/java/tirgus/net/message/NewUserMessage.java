package tirgus.net.message;

import tirgus.model.User;
import tirgus.serialization.CSVSerializer;

public class NewUserMessage extends TirgusMessage
{
    private User user;

    public NewUserMessage(String body)
    {
        super(body);

        this.user = CSVSerializer.read(body, User.class);
    }

    public NewUserMessage(User user)
    {
        this(CSVSerializer.write(user));
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
