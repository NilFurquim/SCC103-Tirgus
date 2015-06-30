package tirgus.model;

import org.junit.Test;
import tirgus.security.Password;
import tirgus.serialization.CSVSerializer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Test for users
 */
public class UserTest {

    @Test
    public void testSerialization() {
        User originalUser = new User("name", "address", "telephone", "email", "login",
                new Password(UUID.randomUUID().toString()));

        String output = CSVSerializer.write(originalUser);

        User replicatedUser = CSVSerializer.read(output, User.class);
        assertEquals(replicatedUser.getName(), originalUser.getName());
        assertEquals(replicatedUser.getAddress(), originalUser.getAddress());
        assertEquals(replicatedUser.getTelephone(), originalUser.getTelephone());
        assertEquals(replicatedUser.getEmail(), originalUser.getEmail());
        assertEquals(replicatedUser.getLogin(), originalUser.getLogin());
        assertEquals(replicatedUser.getPassword().getEncryptedPassword(),
                originalUser.getPassword().getEncryptedPassword());
        assertEquals(replicatedUser.getPassword().getEncodedSalt(),
                originalUser.getPassword().getEncodedSalt());
    }
}