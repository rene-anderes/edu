package org.anderes.edu.sha1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class TheSHA1Calculator implements TheCalculator {

    @Override
    public ResultData eval(Path theFile) throws FileNotFoundException, IOException {

        try (final FileInputStream input = new FileInputStream(theFile.toFile())) {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1) {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }

            final String marshal = new HexBinaryAdapter().marshal(sha1.digest());
            return new ResultData(theFile, marshal);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            System.exit(99);
            return null;
        } 
    }

}
