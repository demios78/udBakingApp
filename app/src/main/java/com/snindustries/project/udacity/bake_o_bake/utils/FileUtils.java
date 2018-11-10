package com.snindustries.project.udacity.bake_o_bake.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shaaz Noormohammad
 * (c) 10/12/18
 */
public class FileUtils {

    /**
     * Gets the contents of a file in raw folder
     */
    public static String getRawResource(Context context, int resource) {
        String res = null;
        InputStream is = context.getResources().openRawResource(resource);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1];
        try {
            while (is.read(b) != -1) {
                baos.write(b);
            }
            ;
            res = baos.toString();
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
