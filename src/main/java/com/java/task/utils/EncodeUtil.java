package com.java.task.utils;

import com.java.task.Response.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

public class EncodeUtil {

    private static String salt = "mnbvhchxhzasdfghjk";
    private static byte[] iv = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2};

    public EncodeUtil() {
    }

    public static String base64(String string) throws Exception {
        if (StringUtils.isEmpty(string)) {
            return string;
        } else {
            try {
                return Base64.getEncoder().encodeToString(string.getBytes("utf-8"));
            } catch (UnsupportedEncodingException var2) {
                throw new Exception("Failed to encode string " + string);
            }
        }
    }

    public static byte[] DecodeBase64(String string) throws Exception {
        if (StringUtils.isEmpty(string)) {
            return string.getBytes();
        } else {
            String[] strings = string.split(",");
            return strings.length > 1 ? Base64.getDecoder().decode(strings[1].getBytes(StandardCharsets.UTF_8)) : Base64.getDecoder().decode(string.getBytes(StandardCharsets.UTF_8));
        }
    }
//AES_256 encroption

    public static String encrypt(String strToEncrypt, String secret) throws Exception {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 100, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception var8) {
            throw new Exception("Error while encrypting message");
        }
    }

    public static String decrypt(String strToDecrypt, String secret) throws Exception {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 100, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception var8) {
            throw new Exception("Error while decrypting message");
        }
    }

    public static String generateSecret() {
        return UUID.randomUUID().toString();
    }

    public static UserToken getUserTokenByAuthorizationHeader(String token) {
        try {
            String decripedToken = EncodeUtil.decrypt(token, JsonUtil.secretKey);
            System.out.println(decripedToken);
            UserToken userToken = JsonUtil.getObjectFromJsonString(decripedToken, UserToken.class);
            return userToken;
        } catch (Exception e) {
            return null;
        }
    }
}




