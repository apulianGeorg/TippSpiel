package com.example.tippspiel.basics;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Tipp.SpielTipp;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.Tipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyJsonWriter {

    public static void write() {
        String jsonStr= TippManager.getJsonString();
        writeToSDFile(jsonStr);
    }

    private static void writeToSDFile(String  inpStr){
        ///storage/emulated/0/downloadTippFile.txt
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, InternalConstants.TippFile);

        try {
            FileOutputStream f = new FileOutputStream(file,false);
            f.write(inpStr.getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
