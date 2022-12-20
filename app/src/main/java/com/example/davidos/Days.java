package com.example.davidos;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Days {

    private static String filename = "edus";
    private static Context currentContext;
    private static Data data;
    public static ArrayList<String> today;
    public static ArrayList<String> allEDU;
    public static GsonBuilder build = new GsonBuilder();
    public static Gson gson = build.create();
    public static void getSavedData() throws IOException {


        FileInputStream in = null;

        try {
            in = currentContext.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            initData();
            in = currentContext.openFileInput(filename);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        inputStreamReader.close();

        System.out.println(sb);

        data = gson.fromJson(sb.toString(), Data.class);
        allEDU = data.getAll();
        System.out.println(data);
        System.out.println(("All EDU:"));
        System.out.println(allEDU);


        System.out.println(("Today:"));
        System.out.println(data.getDay(getDate()));

        today = data.getDayEDU(getDate());
        System.out.println(("today EDU:"));
        System.out.println(today);



        System.out.println("Made EDU arrays!");




    }

    public static ArrayList<String> convertToArrayList(JSONArray jarray) throws JSONException {
        ArrayList<String> listdata = new ArrayList<String>();
        if (jarray != null) {
            for (int i=0;i<jarray.length();i++){
                listdata.add(jarray.getString(i));
            }
        }
        return listdata;
    }
    public static JSONObject createNewDay(JSONArray currentEDUs) throws JSONException {
        JSONObject newObject = new JSONObject();
        newObject.put("edu", currentEDUs);

        return newObject;
    }
    public static void setContext(Context context) {
        currentContext = context;
        try {
            System.out.println("Initiated Data!");
            getSavedData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void saveData() {
//        String content = TextUtils.join(",", items);
//        try (FileOutputStream fos = currentContext.openFileOutput(filename, Context.MODE_PRIVATE)) {
//            fos.write(content.getBytes(StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static String jsify(ArrayList list) {
        return "[\"" + TextUtils.join("\",\"", list) + "\"]";
    }

    public static void saveData() {
        String content = null;

        content = gson.toJson(data);
        System.out.println(content);
        try (FileOutputStream fos = currentContext.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearData() {
        String content = "";
        try (FileOutputStream fos = currentContext.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getDate() {
        DateTimeFormatter dateF = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(dateF);
        return date;
    }
    public static void initData() {

        String content = "{" +
                "'allEdu':[], " +
                "days: {" +
                "'" + getDate() + "': {'edu':[]}}" +
                "}"
                ;
        try (FileOutputStream fos = currentContext.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeEDU(int i) {
        allEDU.remove(i);
        saveData();
    }
    public static void completeEDU(int i) {
        today.remove(i);
        saveData();
    }
    public static void addEDU(String text) {
        today.add(text);
        allEDU.add(text);
        saveData();
    }


}

class Data {
    private List<String> allEdu;
    private HashMap<String, HashMap<String, List<String>>> days;
    public Data() {}

    public ArrayList<String> getAll() {
        return (ArrayList<String>) allEdu;
    }
    public HashMap<String, List<String>> getDay(String day) {
        return days.get(day);
    }
    public ArrayList<String> getDayEDU(String day) {
        return (ArrayList<String>) days.get(day).get("edu");
    }


}