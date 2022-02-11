package com.example.logistics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //license
    static public String LICENSE_SERVER_PATH = "1k60AtjPiqYzlFJMjlpqxikVLBwoHopn3DOR7jjIZAjg";
    static public String LICENSE_SHEET_RANGE = "A1:D";
    static public String LICENSE_SHEET_NAME = "License";
    static public int LICENSE_CUS_ID = 0;
    static public int LICENSE_CUS_NAME = 1;
    static public int LICENSE_CUS_EXPIRE_DATE = 2;
    static public int LICENSE_CUS_NOTE = 3;
    static public String customerLicenseID = "37VM0001LG";
    static public String licenseExpireDate;
    static public int licenseRemainDays;





    //database
    static public final String GOOGLE_SHEET_SPREADSHEET_ID = "1f5VBaiZVyq5D9c1Nb0SO2nElNzvUQGdCP1VlerWSoM4";


    // table ID for hàng hóa




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
