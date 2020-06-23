package pl.jkiakumbo.MySocialNetworking.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderUtil {
    public static String encode(String stringToEncode){
        return new BCryptPasswordEncoder().encode(stringToEncode);
    }
}
