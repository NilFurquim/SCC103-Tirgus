package tirgus.security;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PasswordTest {

    @Test
    public void testEncryption()
    {
        Password password = new Password(UUID.randomUUID().toString());
        Password matchingPassword = new Password(password.getEncodedSalt(), password.getEncryptedPassword(), true);
        assertEquals(matchingPassword.getEncodedSalt(), password.getEncodedSalt());
        assertEquals(matchingPassword.getEncryptedPassword(), password.getEncryptedPassword());
    }
}