package com.example.tippspiel.basics;

import com.example.tippspiel.InternalConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFileReader {

    public static  String readFile(){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        File file = new File(dir, InternalConstants.TippFile);
        try {
            java.io.FileReader fileReader= new java.io.FileReader(file);
            char[] chars = new char[(int) file.length()];
            //noinspection ResultOfMethodCallIgnored
            fileReader.read(chars);
            return new String(chars);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return InternalConstants.EmptyStr;
    }
}
