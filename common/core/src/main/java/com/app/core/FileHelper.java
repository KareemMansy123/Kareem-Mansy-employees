package com.app.core;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.core.domain.Employee;
import com.app.core.requster.ActivityResultRequester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class FileHelper {
    public static final String Home_TAG = "MainActivity";
    private String actualized = "";
    private String fullError = "";
    private List<Employee> employees = new ArrayList<>();
    private PassData passData;

    @SuppressLint("NewApi")
    public void showFileChooser(AppCompatActivity activity,PassData passData) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        this.passData = passData;
        new ActivityResultRequester(activity).request(intent, data -> {
            if (data.getResultCode() == RESULT_OK) {
                try {
                    Uri imageUri = data.getData() != null ? data.getData().getData() : null;
                    String tempID = "", id = "";
                    assert data.getData() != null;
                    Uri uri = data.getData().getData();
                    Log.e(Home_TAG, "file auth is " + uri.getAuthority());
                    fullError = fullError + "file auth is " + uri.getAuthority();
                    switch (imageUri.getAuthority()) {
                        case "media": {
                            tempID = imageUri.toString();
                            tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                            id = tempID;
                            Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            String selector = MediaStore.Images.Media._ID + "=?";
                            actualized = new FileHelper().getColumnData(contenturi, selector, new String[]{id}, activity);
                            break;
                        }
                        case "com.android.providers.media.documents": {
                            tempID = DocumentsContract.getDocumentId(imageUri);
                            String[] split = tempID.split(":");
                            String type = split[0];
                            id = split[1];
                            Uri contenturi = null;
                            switch (type) {
                                case "image":
                                    contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                    break;
                                case "video":
                                    contenturi = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                    break;
                                case "audio":
                                    contenturi = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                    break;
                            }
                            String selector = "_id=?";
                            actualized = new FileHelper().getColumnData(contenturi, selector, new String[]{id}, activity);
                            break;
                        }
                        case "com.android.providers.downloads.documents": {
                            tempID = imageUri.toString();
                            tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                            id = tempID;
                            Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                            // String selector = MediaStore.Images.Media._ID+"=?";
                            actualized = new FileHelper().getColumnData(contenturi, null, null, activity);
                            break;
                        }
                        case "com.android.externalstorage.documents": {
                            tempID = DocumentsContract.getDocumentId(imageUri);
                            String[] split = tempID.split(":");
                            String type = split[0];
                            id = split[1];
                            Uri contenturi = null;
                            if (type.equals("primary")) {
                                actualized = Environment.getExternalStorageDirectory() + "/" + id;
                            }
                            break;
                        }
                    }
                    File myFile;
                    String temppath = uri.getPath();
                    if (temppath.contains("//")) {
                        temppath = temppath.substring(temppath.indexOf("//") + 1);
                    }
                    fullError = fullError + "\n" + " file details -  " + actualized + "\n --" + uri.getPath() + "\n--" + temppath;
                    if (actualized.equals("") || actualized.equals(" ")) {
                        myFile = new File(temppath);
                    } else {
                        myFile = new File(actualized);
                    }
                    employees = new FileParser().parse(myFile, FileParser.Pattern.Comma);
//                        textView.setText(new FileHelper().readfile(myFile));
                    Log.e("BeforInter", " read errro " + employees.toString());

                    passData.passEmployeeData(new SortEmployee().sortEmployee(employees));
                } catch (Exception e) {
                    Log.e(Home_TAG, " read errro " + e.toString());
                }
            }
            return null;
        });
//        try {
//            activity.startActivityForResult(  Intent.createChooser(intent, "Select a File to Upload"),  FILE_SELECT_CODE);
//        } catch (Exception e) {
//            Log.e("Home_TAG", " choose file error "+e.toString());
//        }
    }

    @SuppressLint("Range")
    public String getColumnData(Uri uri, String selection, String[] selectarg, Activity activity) {
        String filepath = "";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor = activity.getContentResolver().query(uri, projection, selection, selectarg, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.e(Home_TAG, " file path is " + cursor.getString(cursor.getColumnIndex(colunm)));
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor != null)
            cursor.close();
        return filepath;
    }

    public String readfile(File file) {
        StringBuilder builder = new StringBuilder();
        Log.e("mainredfile", "read start");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            br.close();
        } catch (Exception e) {
            Log.e("main", " error is " + e.toString());
        }
        Log.d("FileParser1", "parse: " + builder.toString());
        return builder.toString();
    }
}
