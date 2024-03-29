package com.example.logistics;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleSheetInterface extends Activity {


    Sheets sheetsService;
    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory factory = JacksonFactory.getDefaultInstance();
    // private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String CREDENTIALS_FILE_PATH = "/quickstart37-cb11b3794e30.json";



    public List<List<Object>> getData(String SPREADSHEET_ID,String sheetName, String cellName){
        String range = sheetName+"!"+cellName;
        List<List<Object>> data = new ArrayList<>();
        GoogleCredential cred = null;
        // InputStream stream = getResources().openRawResource(
        //       getResources().getIdentifier("secret",
        //             "raw", getPackageName()));
        InputStream stream = GoogleSheetInterface.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
                try {
                    cred = GoogleCredential.fromStream(stream)
                            .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sheetsService = new Sheets.Builder(transport, factory, cred)
                        .setApplicationName("GoogleSheetHelper")
                        .build();
                try {
                    ValueRange result = sheetsService.spreadsheets().values()
                            .get(SPREADSHEET_ID, range)
                            .execute();
                      data = result.getValues();
                      //MainActivity.googleSheetDownloadSts =1; //update status

                } catch (IOException e) {
                    Log.e("GSHelper", e.getLocalizedMessage());
                   // MainActivity.googleSheetDownloadSts =0;
                }
        return data;
    }


    public boolean updateRangeData(String SPREADSHEET_ID,String sheetName, String cellName, List<List<Object>> listData){
        UpdateValuesResponse response = null;
        boolean status =false;
        String range = sheetName+"!"+cellName;
        GoogleCredential cred = null;
        // InputStream stream = getResources().openRawResource(
        //       getResources().getIdentifier("secret",
        //             "raw", getPackageName()));
        InputStream stream = GoogleSheetInterface.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        try {
            cred = GoogleCredential.fromStream(stream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheetsService = new Sheets.Builder(transport, factory, cred)
                .setApplicationName("GoogleSheetHelper")
                .build();

                ValueRange valueRange = new ValueRange();
                valueRange.setMajorDimension("ROWS");
                valueRange.setRange(range);
                //valueRange.setValues(values1);
                valueRange.setValues(listData);
                try {
                    response = sheetsService.spreadsheets().values()
                            .update
                                    (SPREADSHEET_ID,
                                            range, valueRange).setValueInputOption("USER_ENTERED")
                            .execute();
                   // MainActivity.googleSheetUploadoadSts=1; //update status
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response == null) {
                    status = false;
                }else {
                    status = true;
                }
                return status;
    }

    public int getTotalRows(String SPREADSHEET_ID,String sheetName){
        String range = sheetName+"!A1:B";
    int totalRows = -1;
        GoogleCredential cred = null;
        // InputStream stream = getResources().openRawResource(
        //       getResources().getIdentifier("secret",
        //             "raw", getPackageName()));
        InputStream stream = GoogleSheetInterface.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        try {
            cred = GoogleCredential.fromStream(stream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        } catch (IOException e) {
            e.printStackTrace();
            totalRows = -1;
        }
        sheetsService = new Sheets.Builder(transport, factory, cred)
                .setApplicationName("GoogleSheetHelper")
                .build();
        try {
            ValueRange result = sheetsService.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            totalRows = result.getValues().size();

        } catch (IOException e) {
            Log.e("GSHelper", e.getLocalizedMessage());
            totalRows = -1;
        }
        return totalRows;
    }

    public boolean deleteAllData (String SPREADSHEET_ID, String sheetName, String cellName){
        ClearValuesResponse response = null;
        boolean status =false;

        String range = sheetName+"!"+cellName;
        //List<List<Object>> data = new ArrayList<>();
        GoogleCredential cred = null;
        // InputStream stream = getResources().openRawResource(
        //       getResources().getIdentifier("secret",
        //             "raw", getPackageName()));
        InputStream stream = GoogleSheetInterface.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        try {
            cred = GoogleCredential.fromStream(stream)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheetsService = new Sheets.Builder(transport, factory, cred)
                .setApplicationName("GoogleSheetHelper")
                .build();

        ClearValuesRequest requestBody = new ClearValuesRequest();
        try {
            Sheets.Spreadsheets.Values.Clear request = sheetsService.spreadsheets().values().clear(SPREADSHEET_ID,range,requestBody);
            response = request.execute();

        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }
        if (response == null) {
            status = false;
        }else {
            status = true;
        }
        return status;

    }


};