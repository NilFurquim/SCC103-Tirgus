package tirgus.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Password
{
    private static final int saltSize = 16;
    private static final int digestTimes = 1009;

    private String salt;
    private String password;

    public Password(String plainPassword)
    {
        salt = generateSalt();
        password = encrypt(plainPassword);
    }

    public boolean matches(String plainPassword)
    {
        return password.equals(encrypt(plainPassword));
    }

    private String encrypt(String plainPassword)
    {
        byte[] passBytes = (salt + plainPassword).getBytes(Charset.forName("UTF-8"));
        try
        {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            for (int i = 0; i < digestTimes; ++i)
            {
                passBytes = sha512.digest(passBytes);
            }
        } catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }

        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(passBytes));
    }

    private static String generateSalt()
    {
        try
        {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] saltBytes = new byte[saltSize];
            random.nextBytes(saltBytes);
            Base64.Encoder base64 = Base64.getEncoder();
            base64.encode(saltBytes);
            return new String(saltBytes);
        }catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
