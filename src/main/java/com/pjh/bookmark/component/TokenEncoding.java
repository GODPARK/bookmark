package com.pjh.bookmark.component;

import com.pjh.bookmark.exception.UnAuthException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class TokenEncoding {
    private String iv;
    private Key key;

    final static String secretKey = "iloveleebitna1228";

    public TokenEncoding() {
        try{
            this.iv = secretKey.substring(0,16);
            byte[] keyBytes = new byte[16];
            byte[] b = secretKey.getBytes("UTF-8");
            int len = b.length;

            if ( len > keyBytes.length ){
                len =  keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            this.key = keySpec;
        }
        catch ( UnsupportedEncodingException e){
            throw new UnAuthException(e.getMessage());
        }
    }

    public String encrypt(String str) {
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            Base64.Encoder encoder = Base64.getEncoder();
            String enStr = new String(encoder.encode(encrypted));
            return enStr;
        }
        catch (NoSuchAlgorithmException e1){
            throw new UnAuthException(e1.getMessage());
        }
        catch (GeneralSecurityException e2){
            throw new UnAuthException(e2.getMessage());
        }
        catch (UnsupportedEncodingException e3){
            throw new UnAuthException(e3.getMessage());
        }
    }

    public String decrypt(String str) {
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(str.getBytes());
            return new String(cipher.doFinal(bytes),"UTF-8");
        }
        catch (NoSuchAlgorithmException e1){
            throw new UnAuthException(e1.getMessage());
        }
        catch (GeneralSecurityException e2){
            throw new UnAuthException(e2.getMessage());
        }
        catch (UnsupportedEncodingException e3){
            throw new UnAuthException(e3.getMessage());
        }
    }
}
