package tirgus.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Password encryption
 */
public class Password
{
    private static final int saltSize = 16;
    private static final int digestTimes = 1009;

    private String encodedSalt;
    private String encryptedPassword;

    /**
     * Construction
     * @param plainPassword
     */
    public Password(String plainPassword)
    {
        encodedSalt = generateSalt();
        encryptedPassword = encrypt(encodedSalt, plainPassword);
    }

    /**
     * Construction
     */
    public Password(String encodedSalt, String password, boolean encrypted)
    {
        this.encodedSalt = encodedSalt;
        if (encrypted)
        {
            this.encryptedPassword = password;
        } else
        {
            this.encryptedPassword = encrypt(encodedSalt, password);
        }
    }

    /**
     * Perform encryption
     * @param encodedSalt
     * @param plainPassword
     * @return
     */
    public static String encrypt(String encodedSalt, String plainPassword)
    {
        String decodedSalt = new String(Base64.getDecoder().decode(encodedSalt));
        byte[] passBytes = (decodedSalt + plainPassword).getBytes(Charset.forName("UTF-8"));
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

    /**
     * Generate salt
     * @return
     */
    public static String generateSalt()
    {
        try
        {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] saltBytes = new byte[saltSize];
            random.nextBytes(saltBytes);
            Base64.Encoder base64 = Base64.getEncoder();
            return base64.encodeToString(saltBytes);
        }catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public String getEncodedSalt()
    {
        return encodedSalt;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }
}
