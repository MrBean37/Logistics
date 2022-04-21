package com.example.logistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //license
    static public String LICENSE_SERVER_PATH = "1k60AtjPiqYzlFJMjlpqxikVLBwoHopn3DOR7jjIZAjg";
    static public String LICENSE_SHEET_RANGE = "A2:D";
    static public String LICENSE_SHEET_NAME = "License";
    static public int LICENSE_CUS_ID = 0;
    static public int LICENSE_CUS_NAME = 1;
    static public int LICENSE_CUS_EXPIRE_DATE = 2;
    static public int LICENSE_CUS_NOTE = 3;
    static public String customerLicenseID = "37VM0001LG";
    static public String licenseExpireDate="";
    static public int licenseRemainDays;
    static public List<List<Object>> licenseInfor;
    static public int licenseSheetDownloadSts=0;
    //static public int licenseSheetUploadoadSts=0;

    static public final String LICENSE_SHARED_PREFS_NAME ="license";
    static public final String LICENSE_SHARED_PREFS_ID ="licenseID";
    static public final String LICENSE_SHARED_PREFS_EXP ="licenseExpireDate";




    //database
    static public final String GOOGLE_SHEET_SPREADSHEET_ID = "1f5VBaiZVyq5D9c1Nb0SO2nElNzvUQGdCP1VlerWSoM4";


    // googlesheet upload and download status
    static public int googleSheetDownloadSts=0;
    static public int googleSheetUploadoadSts=0;


    // table ID for hàng hóa

    //Fragment
    static public ViewPager mainViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check permission
        if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        }



        //licenseLocalSaveData("a","b");
        // license information
        final GoogleSheetInterface googleSheetInterface =new GoogleSheetInterface();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                licenseInfor = googleSheetInterface.getData(LICENSE_SERVER_PATH,LICENSE_SHEET_NAME,LICENSE_SHEET_RANGE);
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        licenseSheetDownloadSts = googleSheetDownloadSts; //set download status from google sheet to license status

        LicenseCheck licenseCheck =new LicenseCheck();
        try {
            licenseCheck.licenseStatusUpdate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //update license
        if (licenseSheetDownloadSts ==1){
            licenseLocalSaveData(customerLicenseID,licenseExpireDate);
        }else{
            licenseLocalRetriveData(customerLicenseID,licenseExpireDate);
        }

        // end of license check



        if (googleSheetDownloadSts ==1 ){
            Toast.makeText(this,"update OK",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"update Not OK",Toast.LENGTH_SHORT).show();
        }

        setContentView(R.layout.activity_main);
        mainViewPager = findViewById(R.id.main_view_page);
        FloatingActionButton mainFab =findViewById(R.id.main_fab);
        final FloatingActionButton summaryFab =findViewById(R.id.summary_fab);
        final FloatingActionButton goodsFab =findViewById(R.id.goods_fab);
        final FloatingActionButton scanFab =findViewById(R.id.scan_fab);
        final FloatingActionButton searchFab =findViewById(R.id.search_fab);
        final FloatingActionButton uploadFab =findViewById(R.id.upload_fab);
        final FloatingActionButton downloadFab =findViewById(R.id.download_fab);

        //set float button visible and invisible
        //at begining all float button have to invisible
        summaryFab.setVisibility(View.GONE);
        goodsFab.setVisibility(View.GONE);
        scanFab.setVisibility(View.GONE);
        searchFab.setVisibility(View.GONE);
        uploadFab.setVisibility(View.GONE);
        downloadFab.setVisibility(View.GONE);

        mainFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(summaryFab.getVisibility()==View.GONE){
                    summaryFab.setVisibility(View.VISIBLE);
                }else {
                    summaryFab.setVisibility(View.GONE);
                }

                if(goodsFab.getVisibility()==View.GONE){
                    goodsFab.setVisibility(View.VISIBLE);
                }else {
                    goodsFab.setVisibility(View.GONE);
                }

                if(scanFab.getVisibility()==View.GONE){
                    scanFab.setVisibility(View.VISIBLE);
                }else {
                    scanFab.setVisibility(View.GONE);
                }

                if(searchFab.getVisibility()==View.GONE){
                    searchFab.setVisibility(View.VISIBLE);
                }else {
                    searchFab.setVisibility(View.GONE);
                }

                if(uploadFab.getVisibility()==View.GONE){
                    uploadFab.setVisibility(View.VISIBLE);
                }else {
                    uploadFab.setVisibility(View.GONE);
                }

                if(downloadFab.getVisibility()==View.GONE){
                    downloadFab.setVisibility(View.VISIBLE);
                }else {
                    downloadFab.setVisibility(View.GONE);
                }

            }
        });

        // create default data for testing
        GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(this);
        goodsLocalDatabase.createDefaultDatabase();


        setupViewPager();
        summaryFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(0);
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
            }
        });

        goodsFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(1);
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
            }
        });


    }

    public void licenseLocalSaveData(String id, String expDate){
        SharedPreferences sharedPreferences =getSharedPreferences(LICENSE_SHARED_PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(LICENSE_SHARED_PREFS_ID,id);
        editor.putString(LICENSE_SHARED_PREFS_EXP,expDate);
        editor.apply();

    }

    public void licenseLocalRetriveData(String id, String expDate){
        SharedPreferences sharedPreferences =getSharedPreferences(LICENSE_SHARED_PREFS_NAME,MODE_PRIVATE);
        id=sharedPreferences.getString(LICENSE_SHARED_PREFS_ID,"");
        expDate = sharedPreferences.getString(LICENSE_SHARED_PREFS_EXP,"");

    }

    public void licenseCheck() throws ParseException {

        if(MainActivity.licenseSheetDownloadSts ==1) {
            for (int i = 0; i < MainActivity.licenseInfor.size(); i++) {
                if (MainActivity.licenseInfor.get(i).get(MainActivity.LICENSE_CUS_ID).toString().matches(MainActivity.customerLicenseID)) {
                    MainActivity.licenseExpireDate = MainActivity.licenseInfor.get(i).get(MainActivity.LICENSE_CUS_EXPIRE_DATE).toString();

                    // update license expire date to local database
                    //licenseLocalSaveData(MainActivity.customerLicenseID,MainActivity.licenseExpireDate);
                    //licenseLocalSaveData("a","b");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date expDate = sdf.parse(MainActivity.licenseExpireDate);
                    String currentDateString = sdf.format(new Date());
                    Date currentDate = sdf.parse(currentDateString);
                    long diff = expDate.getTime() - currentDate.getTime();
                    MainActivity.licenseRemainDays = (int) (diff / (1000 * 60 * 60 * 24));

                }else{
                    //read license infor from local database when it not no result searching
                    MainActivity.licenseRemainDays =-1;
                    MainActivity.licenseExpireDate="NA";

                }
            }
        }else{
            //  licenseLocalRetriveData(MainActivity.customerLicenseID,MainActivity.licenseExpireDate);
        }
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SummaryFragment(), "Summary");
        adapter.addFragment(new GoodsFragment(), "Goods Infor");
        adapter.addFragment(new GoodsSearchFragment(),"Goods Search");

        mainViewPager.setAdapter(adapter);
    }
}
