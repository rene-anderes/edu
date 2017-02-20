package org.anderes.edu.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileSecurity {

    public static SecretObject encrypt(final String password, final Path inputFile, final Path outputFile) throws Exception {

        final byte[] salt = new byte[8];
        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        final SecretKey secretKey = factory.generateSecret(keySpec);
        final SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        
        try (InputStream inFile = new FileInputStream(inputFile.toFile()); 
             OutputStream outFile = new FileOutputStream(outputFile.toFile())) {
            byte[] input = new byte[64];
            int bytesRead;
            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    outFile.write(output);
            }
            
            byte[] output = cipher.doFinal();
            if (output != null) {
                outFile.write(output);
            }
        }
        final AlgorithmParameters params = cipher.getParameters();
        final byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        return new SecretObject(salt, iv);
    }

    public static String decrypt(final String password, final SecretObject secretObject, final Path securityFile) throws Exception {
        
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), secretObject.getSalt(), 65536, 256);
        final SecretKey tmp = factory.generateSecret(keySpec);
        final SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(secretObject.getInitializationVector()));
        
        try (FileInputStream fis = new FileInputStream(securityFile.toFile());
             ByteArrayOutputStream fos = new ByteArrayOutputStream()){
            byte[] in = new byte[64];
            int read;
            while ((read = fis.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null) {
                    fos.write(output);
                }
            }
            
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }
            return new String(fos.toByteArray());
        }
    }

}
