package org.anderes.edu.security;

public class SecretObject {

    private final byte[] initializationVector;
    private final byte[] salt;

    public SecretObject(byte[] salt, byte[] iv) {
        this.salt = salt;
        this.initializationVector = iv;
    }
    
    public byte[] getInitializationVector() {
        return initializationVector;
    }
    
    public byte[] getSalt() {
        return salt;
    }

}
