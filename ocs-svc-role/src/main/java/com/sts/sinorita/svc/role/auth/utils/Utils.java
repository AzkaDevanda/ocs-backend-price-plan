package com.sts.sinorita.svc.role.auth.utils;


import com.sts.sinorita.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Component
public class Utils {

//    @Value()
    static String secretKey ="1234567890123456" ;
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public void validatePin(String pinSource, String pin) throws Exception {
        String pinEncode = encryptPin(pin);

        log.info("pin encode : {}",pinEncode);
        log.info("pin source : {}",pinSource);

        if (!pinEncode.equalsIgnoreCase(pinSource)){
            throw new ValidationHandler("PIN Tidak sesuai");
        }
    }
    // Method to decrypt the PIN
    public String decryptPin(String encryptedPin) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPin);
        byte[] decryptedPin = cipher.doFinal(decodedBytes);
        return new String(decryptedPin);
    }

    public String encryptPin(String pin) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedPin = cipher.doFinal(pin.getBytes());
        return Base64.getEncoder().encodeToString(encryptedPin);
    }
}
