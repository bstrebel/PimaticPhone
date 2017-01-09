package de.digitec.pimatic.utils;

/**
 * Created by BST on 06.01.2017.
 */

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Debug {

    /**
    * Use android.util.Log for debug output
    */
    static public void v(String tag, String message) {
        Log.v(tag, message);
    }
    static public void d(String tag, String message) {
        Log.d(tag, message);
    }
    static public void i(String tag, String message) {
        Log.i(tag, message);
    }
    static public void w(String tag, String message) {
        Log.w(tag, message);
    }
    static public void e(String tag, String message) {
        Log.e(tag, message);
    }

    /**
     * Use properties file on external storage for creditials
     */
    public static Secret getSecret() {
        Properties properties = null;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String path = Environment.getExternalStorageDirectory().getPath() +
                           "/Secret/PimaticPhone.properties";
            File propertyFile = new File(path);
            if (propertyFile.exists()) {
                properties = new Properties();
                InputStream input = null;
                try {
                    input = new FileInputStream(propertyFile);
                    properties.load(input);
                    return new Secret(
                            properties.getProperty("username"),
                            properties.getProperty("password"),
                            properties.getProperty("server")
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Secret getSecret(String server) {
        Secret secret = getSecret();
        secret.setServer(server);
        return secret;
    }
}
