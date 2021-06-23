package com.example.tippspiel.basics;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Tipp.SpielTipp;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.frontend.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyJsonWriter {

    public static void write(List<Tipper> tipperList){
        //Creating a JSONObject object

        //Inserting key-value pairs into the json object
        try {
            JSONArray jsonTipperArr = new JSONArray();
            for (Tipper tipper : tipperList) {
                JSONObject jsonTipperObject = new JSONObject();
                JSONArray jsonSpielTippArr = new JSONArray();
                jsonTipperObject.put(InternalConstants.TipperId, tipper.getTipperId());
                jsonTipperObject.put(InternalConstants.TipperName, tipper.getName());
                jsonTipperObject.put(InternalConstants.TipperPunkte, tipper.getPunkte());

                for (SpielTipp spielTipp: tipper.getSpielTippList()) {
                    JSONObject jsonSpielTippObject = new JSONObject();
                    jsonSpielTippObject.put(InternalConstants.TipperSpielId, spielTipp.getSpielId());
                    jsonSpielTippObject.put(InternalConstants.TipperSpielErgebnis, spielTipp.getErgebnis());
                    jsonSpielTippObject.put(InternalConstants.TipperSpielIsEvaluated, spielTipp.isEvaluated());
                    jsonSpielTippArr.put(jsonSpielTippObject);
                }
                JSONObject tipperSpieleObjekt = new JSONObject();
                tipperSpieleObjekt.put(InternalConstants.TipperSpiele, jsonSpielTippArr);
                jsonTipperArr.put(jsonTipperObject);
            }
            JSONObject tipperObjekt = new JSONObject();
            tipperObjekt.put(InternalConstants.Tipper, jsonTipperArr);
            writeToSDFile(tipperObjekt.toString());

            return;
        } catch ( JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static void writeToSDFile(String  inpStr){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

        File root = android.os.Environment.getExternalStorageDirectory();

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, InternalConstants.TippFile);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.print(inpStr);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
