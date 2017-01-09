package de.digitec.pimatic.utils;

/**
 * Created by BST on 05.01.2017.
 */

import android.util.Base64;

public class Coding {

    public static String EncodeBase64(String source) {
        String encoded = Base64.encodeToString(source.getBytes(), Base64.DEFAULT);
        return encoded;
    }
}
