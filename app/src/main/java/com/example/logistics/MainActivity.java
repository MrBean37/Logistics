package com.example.logistics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //license
    static public String LICENSE_SERVER_PATH = "1k60AtjPiqYzlFJMjlpqxikVLBwoHopn3DOR7jjIZAjg";
    static public String LICENSE_SHEET_RANGE = "A2:F";
    static public String LICENSE_SHEET_NAME = "License";
    static public int LICENSE_MACADDRESS_MAP = 0;
    static public int LICENSE_CUS_NAME_MAP = 1;
    static public int LICENSE_CUS_PHONE_MAP = 2;
    static public int LICENSE_CUS_START_DATE_MAP = 3;
    static public int LICENSE_CUS_EXPIRE_DATE_MAP = 4;
    static public int LICENSE_CUS_NOTE_MAP = 5;

    static public String phoneMacAddress ="";
    static public String licenseCusName ="";
    static public String licenseCusPhone ="";
    static public String licenseStartDate = "";
    static public String licenseExpDate ="";
    static public String licenseNote ="";
    static public int licenseRemainDays;
    static public List<List<Object>> licenseInfor;
    static public int licenseSheetDownloadSts=0;
    //static public int licenseSheetUploadoadSts=0;

    static public final String LICENSE_SHARED_PREFS_NAME ="license";
    static public final String LICENSE_SHARED_PREFS_MACADD ="licenseMacAdress";
    static public final String LICENSE_SHARED_PREFS_CUS_NAME ="licenseCusName";
    static public final String LICENSE_SHARED_PREFS_CUS_PHONE ="licenseCusPhone";
    static public final String LICENSE_SHARED_PREFS_START_DATE ="licenseStartDate";
    static public final String LICENSE_SHARED_PREFS_EXP_DATE ="licenseExpDate";
    static public final String LICENSE_SHARED_PREFS_NOTE ="licenseNote";




    //database
    static public final String DATABASE_SPREADSHEET_ID = "1f5VBaiZVyq5D9c1Nb0SO2nElNzvUQGdCP1VlerWSoM4";
    static public String DATABASE_SHEET_RANGE = "A2:D";
    static public String DATABASE_SHEET_NAME = "License";
    static public List<List<Object>> dataAll;
    //database mapping
    static public int GOODS_ID_MAP=0;
    static public int GOODS_CODE_MAP=1;
    static public int GOODS_NAME_MAP=2;
    static public int GOODS_TYPE_MAP=3;
    static public int GOODS_STS_MAP=4;
    static public int GOODS_QUANTITY_MAP=5;
    static public int GOODS_UNIT_MAP=6;
    static public int GOODS_WEIGHT_MAP=7;
    static public int GOODS_MONEY_MAP=8;
    static public int GOODS_DATE_MAP=9;
    static public int GOODS_LOCATION_MAP=10;
    static public int GOODS_NOTE_MAP=11;

    static public int GOODS_SENDNAME_MAP=12;
    static public int GOODS_SENDID_MAP=13;
    static public int GOODS_SENDPHONE_MAP=14;
    static public int GOODS_SENDCITY_MAP=15;
    static public int GOODS_SENDDISTRICT_MAP=16;
    static public int GOODS_SENDPROVINCE_MAP=17;
    static public int GOODS_SENDCALLED_MAP=18;
    static public int GOODS_SENDDATE_MAP=19;
    static public int GOODS_SENDNOTE_MAP=20;

    static public int GOODS_RECEIVENAME_MAP=21;
    static public int GOODS_RECEIVEID_MAP=22;
    static public int GOODS_RECEIVEPHONE_MAP=23;
    static public int GOODS_RECEIVECITY_MAP=24;
    static public int GOODS_RECEIVEDISTRICT_MAP=25;
    static public int GOODS_RECEIVEPROVINCE_MAP=26;
    static public int GOODS_RECEIVECALLED_MAP=27;
    static public int GOODS_RECEIVEDATE_MAP=28;
    static public int GOODS_RECEIVENOTE_MAP=29;


    // googlesheet upload and download status
    static public int googleSheetDownloadSts=0;
    static public int googleSheetUploadoadSts=0;


    //floating button for main controll buttons
    static public FloatingActionButton mainFab;
    static public FloatingActionButton summaryFab;
    static public FloatingActionButton goodsFab;
    static public FloatingActionButton scanFab;
    static public FloatingActionButton searchFab;
    static public FloatingActionButton uploadFab;
    static public FloatingActionButton downloadFab;
    static public FloatingActionButton goodsAddFab;

    // table ID for hàng hóa
    //Fragment
    static public ViewPager mainViewPager;

    //Context menu and fragment active detect
    //0 -Main; 1 - GoodsFragment; 2 - GoodsSearchFragment; 3-GoodsScanFragment; 4- GoodsAddFragment
    static public Integer curActiveFragment =0;
    static public Integer lastActiveFragment =0;
    static public GoodsInformation goodsSelectFromListview = new GoodsInformation();

    //Scan Result
    static public String scanIDResult ="";

    //General Setting
   // public static String dateFormat ="dd/MM/yyyy";  // date only

    public static String dateFormat ="dd/MM/yyyy HH:mm:ss";  // date and time
    public static boolean hours24HView =true;


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

        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission has not been granted, therefore prompt the user to grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
        }


        //licenseLocalSaveData("a","b");
        // license information
        final GoogleSheetInterface googleSheetInterface =new GoogleSheetInterface();


        // end of license check

        try {
            licenseCheck(this,LICENSE_SERVER_PATH,LICENSE_SHEET_NAME,LICENSE_SHEET_RANGE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        mainViewPager = findViewById(R.id.main_view_page);
        mainFab =findViewById(R.id.main_fab);
        summaryFab =findViewById(R.id.summary_fab);
        goodsFab =findViewById(R.id.goods_fab);
        scanFab =findViewById(R.id.scan_fab);
        searchFab =findViewById(R.id.search_fab);
        uploadFab =findViewById(R.id.upload_fab);
        downloadFab =findViewById(R.id.download_fab);
        goodsAddFab =findViewById(R.id.add_goods_fab);

        //set float button visible and invisible
        //at begining all float button have to invisible
        summaryFab.setVisibility(View.GONE);
        goodsFab.setVisibility(View.GONE);
        scanFab.setVisibility(View.GONE);
        searchFab.setVisibility(View.GONE);
        uploadFab.setVisibility(View.GONE);
        downloadFab.setVisibility(View.GONE);
        goodsAddFab.setVisibility(View.GONE);

        mainFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplication(),MainActivity.licenseExpDate + ";" + MainActivity.licenseRemainDays,Toast.LENGTH_SHORT).show();

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

                if(goodsAddFab.getVisibility()==View.GONE){
                    goodsAddFab.setVisibility(View.VISIBLE);
                }else {
                    goodsAddFab.setVisibility(View.GONE);
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

                lastActiveFragment =  MainActivity.curActiveFragment; //set last fragment
                curActiveFragment = 0; //set active fragment
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
            }
        });

        goodsFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(1);
                lastActiveFragment =  curActiveFragment; //set last fragment
                curActiveFragment = 1; //set active fragment
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
            }
        });

        searchFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(2);
                lastActiveFragment =  curActiveFragment; //set last fragment
                curActiveFragment = 2; //set active fragment
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
            }
        });

        scanFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(3);
                lastActiveFragment =  curActiveFragment; //set last fragment
                curActiveFragment = 3; //set active fragment
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
            }
        });

        goodsAddFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(4);
                lastActiveFragment =  curActiveFragment; //set last fragment
                curActiveFragment = 4; //set active fragment
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
            }
        });

        uploadFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);
                boolean uploadResult = uploadData2Server(getApplication());
                if (uploadResult){

                    Toast.makeText(getApplicationContext(), "Cập Nhật Dữ Liệu Lên Thành Công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Cập Nhật Dữ Liệu Lên Không Thành Công", Toast.LENGTH_SHORT).show();
                }

            }
        });

        downloadFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
                goodsAddFab.setVisibility(View.GONE);

                boolean downloadResult = downloadDataFromServer(getApplication());

                if (downloadResult){
                    Toast.makeText(getApplicationContext(), "Cập Nhật Dữ Xuống Lên Thành Công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Cập Nhật Dữ Liệu Xuống Không Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void licenseLocalSaveData(String macAddress, String cusName,String cusPhone, String startDate,String expDate,String note){
        SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.LICENSE_SHARED_PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(LICENSE_SHARED_PREFS_MACADD,macAddress);
        editor.putString(LICENSE_SHARED_PREFS_CUS_NAME,cusName);
        editor.putString(LICENSE_SHARED_PREFS_CUS_PHONE,cusPhone);
        editor.putString(LICENSE_SHARED_PREFS_START_DATE,startDate);
        editor.putString(LICENSE_SHARED_PREFS_EXP_DATE,expDate);
        editor.putString(LICENSE_SHARED_PREFS_NOTE,note);
        editor.apply();

    }

    public void licenseLocalRetriveData(String macAddress, String cusName, String cusPhone, String startDate, String expDate, String note){
        SharedPreferences sharedPreferences =getSharedPreferences(LICENSE_SHARED_PREFS_NAME,MODE_PRIVATE);
        macAddress=sharedPreferences.getString(LICENSE_SHARED_PREFS_MACADD,"");
        cusName=sharedPreferences.getString(LICENSE_SHARED_PREFS_CUS_NAME,"");
        cusPhone=sharedPreferences.getString(LICENSE_SHARED_PREFS_CUS_PHONE,"");
        startDate=sharedPreferences.getString(LICENSE_SHARED_PREFS_START_DATE,"");
        expDate=sharedPreferences.getString(LICENSE_SHARED_PREFS_EXP_DATE,"");
        note=sharedPreferences.getString(LICENSE_SHARED_PREFS_NOTE,"");
    }

    public static void licenseCheck(final Context context, final String licenseSheetID, final String licenseSheetName, final String licenseCells) throws ParseException {
        final GoogleSheetInterface googleSheetInterface = new GoogleSheetInterface();
        MainActivity.phoneMacAddress = getDeviceMacNumber(context);
        MainActivity.licenseCusName ="NA";
        MainActivity.licenseCusPhone = "NA";
        MainActivity.licenseStartDate = "NA";
        MainActivity.licenseExpDate = "NA";
        MainActivity.licenseNote = "NA";
        MainActivity.licenseRemainDays = -1;

        //List<List<Object>> licenseDatabase = googleSheetInterface.getData(licenseSheetID,licenseSheetName,licenseCells,status);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                    List<List<Object>> licenseDatabase = googleSheetInterface.getData(licenseSheetID,licenseSheetName,licenseCells);
                // read from server
                if(licenseDatabase.size() >0) {
                    for (int i = 0; i < licenseDatabase.size(); i++) {
                        if (licenseDatabase.get(i).get(MainActivity.LICENSE_MACADDRESS_MAP).toString().matches(MainActivity.phoneMacAddress)) {

                            MainActivity.licenseExpDate = licenseDatabase.get(i).get(MainActivity.LICENSE_CUS_EXPIRE_DATE_MAP).toString();
                            MainActivity.licenseCusName = licenseDatabase.get(i).get(MainActivity.LICENSE_CUS_NAME_MAP).toString();
                            MainActivity.licenseCusPhone = licenseDatabase.get(i).get(MainActivity.LICENSE_CUS_PHONE_MAP).toString();
                            MainActivity.licenseStartDate = licenseDatabase.get(i).get(MainActivity.LICENSE_CUS_START_DATE_MAP).toString();
                            MainActivity.licenseNote = licenseDatabase.get(i).get(MainActivity.LICENSE_CUS_NOTE_MAP).toString();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date exp = null;
                            try {
                                exp = sdf.parse(MainActivity.licenseExpDate);
                                String currentDateString = sdf.format(new Date());
                                Date currentDate = sdf.parse(currentDateString);
                                long diff = exp.getTime() - currentDate.getTime();
                                MainActivity.licenseRemainDays = (int) (diff / (1000 * 60 * 60 * 24));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            // update to local database

                            GeneralInformation generalInformation = new GeneralInformation(0,MainActivity.licenseCusName,MainActivity.licenseCusPhone,MainActivity.licenseStartDate,
                                    MainActivity.licenseExpDate,MainActivity.licenseNote,"NA","NA","NA",
                                    "NA","NA");
                            GoodsLocalGeneralDatabase goodsLocalGeneralDatabase =new GoodsLocalGeneralDatabase(context);
                            goodsLocalGeneralDatabase.updateInfor(generalInformation);
                        }
                    }
                } else {  // if can not read data from server then get from local

                    GoodsLocalGeneralDatabase goodsLocalGeneralDatabase = new GoodsLocalGeneralDatabase(context);
                    GeneralInformation licenseLocalInfor = goodsLocalGeneralDatabase.getInforBaseID(0);
                    MainActivity.licenseCusName = licenseLocalInfor.getColumn01();
                    MainActivity.licenseCusPhone = licenseLocalInfor.getColumn02();
                    MainActivity.licenseStartDate = licenseLocalInfor.getColumn03();
                    MainActivity.licenseExpDate = licenseLocalInfor.getColumn04();
                    MainActivity.licenseNote = licenseLocalInfor.getColumn05();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date exp = null;
                    try {
                        exp = sdf.parse(MainActivity.licenseExpDate);
                        String currentDateString = sdf.format(new Date());
                        Date currentDate = sdf.parse(currentDateString);
                        long diff = exp.getTime() - currentDate.getTime();
                        MainActivity.licenseRemainDays = (int) (diff / (1000 * 60 * 60 * 24));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }



            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }



        // if able to read data from server to get license information -> variable status = true



    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SummaryFragment(), "Summary");
        adapter.addFragment(new GoodsFragment(), "Goods Infor");
        adapter.addFragment(new GoodsSearchFragment(),"Goods Search");
        adapter.addFragment(new GoodsScanFragment(),"Goods Scan");
        adapter.addFragment(new GoodsAddFragment(),"Add New Goods");

        mainViewPager.setAdapter(adapter);
    }


    public static void goodsAllInforPopup(final View view, final Context context, final GoodsInformation goodsInformation) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //View popupView = inflater.inflate(R.layout.goods_main_all_infor, null);
        View popupView = LayoutInflater.from(context).inflate(R.layout.goods_main_all_infor, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        TextView goodsMainGoodsID = popupView.findViewById(R.id.goods_main_goods_id);
        TextView goodsMainType = popupView.findViewById(R.id.goods_main_type);
        TextView goodsMainSenderName = popupView.findViewById(R.id.goods_main_sender_name);
        TextView goodsMainSenderPhone = popupView.findViewById(R.id.goods_main_sender_phone);
        TextView goodsMainSenderAddress = popupView.findViewById(R.id.goods_main_sender_address);
        TextView goodsMainSenderDate = popupView.findViewById(R.id.goods_main_sender_date);
        TextView goodsMainReceiveName = popupView.findViewById(R.id.goods_main_receiver_name);
        TextView goodsMainReceivePhone = popupView.findViewById(R.id.goods_main_receiver_phone);
        TextView goodsMainReceiveAddress = popupView.findViewById(R.id.goods_main_receiver_address);
        TextView goodsMainReceiveDate = popupView.findViewById(R.id.goods_main_receiver_date);
        TextView goodsMainSts = popupView.findViewById(R.id.goods_main_goods_sts);
        TextView goodsMainGoodsQuantity = popupView.findViewById(R.id.goods_main_goods_quantity);
        TextView goodsMainMoney = popupView.findViewById(R.id.goods_main_money);
        TextView goodsMainPrice = popupView.findViewById(R.id.goods_main_goods_price);
        TextView goodsMainNote = popupView.findViewById(R.id.goods_main_goods_notes);
        Button goodsMainSenderCallBt = popupView.findViewById(R.id.goods_main_call_sender_bt);
        Button goodsMainReceiveCallBt = popupView.findViewById(R.id.goods_main_call_receiver_bt);
        Button goodsMainUpdateBt = popupView.findViewById(R.id.goods_main_update_bt);
        Button goodsMainCloseBt = popupView.findViewById(R.id.goods_main_close_bt);





        goodsMainGoodsID.setText(goodsInformation.getGoodsCode());
        goodsMainType.setText(goodsInformation.getGoodsType());
        goodsMainSenderName.setText(goodsInformation.getGoodsSendName());
        goodsMainSenderPhone.setText(goodsInformation.getGoodsSendPhone());
        goodsMainSenderAddress.setText(goodsInformation.getGoodsSendProvince()+","+ goodsInformation.getGoodsSendDistrict() +","+goodsInformation.getGoodsSendCity());
        goodsMainSenderDate.setText(goodsInformation.getGoodsSendDate());
        goodsMainReceiveName.setText(goodsInformation.getGoodsReceiveName());
        goodsMainReceivePhone.setText(goodsInformation.getGoodsReceivePhone());
        goodsMainReceiveAddress.setText(goodsInformation.getGoodsReceiveProvince()+","+ goodsInformation.getGoodsReceiveDistrict() +","+goodsInformation.getGoodsReceiveCity());
        goodsMainReceiveDate.setText(goodsInformation.getGoodsReceiveDate());
        //Set status

        if(goodsInformation.getGoodsSts().matches("100")){
            goodsMainSts.setText("Chưa Nhận");
        }else if(goodsInformation.getGoodsSts().matches("200")) {
            goodsMainSts.setText("Đang Ở Văn Phòng");
        }else if(goodsInformation.getGoodsSts().matches("300")) {
            goodsMainSts.setText("Đang Vận Chuyển");
        }else if(goodsInformation.getGoodsSts().matches("400")) {
            goodsMainSts.setText("Đang Ở Kho");
        }else if(goodsInformation.getGoodsSts().matches("500")) {
            goodsMainSts.setText("Đang Giao");
        }else if(goodsInformation.getGoodsSts().matches("600")) {
            goodsMainSts.setText("Đã Hoàn Thành");
        }else{
            goodsMainSts.setText("Không Có Thông Tin");
        }
        goodsMainGoodsQuantity.setText(goodsInformation.getGoodsQuantity());
        goodsMainMoney.setText(goodsInformation.getGoodsMoney());
        goodsMainPrice.setText("chua co data base");
        goodsMainNote.setText(goodsInformation.getGoodsNote());



        goodsMainSenderCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(context, goodsInformation.getGoodsSendPhone());

            }
        });

        goodsMainReceiveCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(context,goodsInformation.getGoodsReceivePhone());

            }
        });

        goodsMainUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                goodsUpdateInforPopup(view,context,goodsSelectFromListview);

            }
        });

        goodsMainCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });




    }


    public static void goodsUpdateInforPopup(View view, final Context context, final GoodsInformation goodsInformation) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //View popupView = inflater.inflate(R.layout.goods_update_all_infor, null);
        View popupView = LayoutInflater.from(context).inflate(R.layout.goods_main_all_add_update, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        final EditText goodsUpdateGoodsID = popupView.findViewById(R.id.goods_update_goods_id);
        final EditText goodsUpdateType = popupView.findViewById(R.id.goods_update_type);
        final EditText goodsUpdateSenderName = popupView.findViewById(R.id.goods_update_sender_name);
        final EditText goodsUpdateSenderPhone = popupView.findViewById(R.id.goods_update_sender_phone);
        final EditText goodsUpdateSenderAddress = popupView.findViewById(R.id.goods_update_sender_address);
        final EditText goodsUpdateSenderDate = popupView.findViewById(R.id.goods_update_sender_date);
        final EditText goodsUpdateReceiveName = popupView.findViewById(R.id.goods_update_receiver_name);
        final EditText goodsUpdateReceivePhone = popupView.findViewById(R.id.goods_update_receiver_phone);
        final EditText goodsUpdateReceiveAddress = popupView.findViewById(R.id.goods_update_receiver_address);
        final EditText goodsUpdateReceiveDate = popupView.findViewById(R.id.goods_update_receiver_date);
        Spinner goodsMainSts = popupView.findViewById(R.id.goods_update_sts);
        final EditText goodsUpdateGoodsQuantity = popupView.findViewById(R.id.goods_update_goods_quantity);
        final EditText goodsUpdateMoney = popupView.findViewById(R.id.goods_update_money);
        final EditText goodsUpdatePrice = popupView.findViewById(R.id.goods_update_goods_price);
        final EditText goodsUpdateNote = popupView.findViewById(R.id.goods_update_goods_notes);
        Button goodsUpdateClearBt = popupView.findViewById(R.id.goods_update_clear_bt);
        Button goodsUpdateReceiveCallBt = popupView.findViewById(R.id.goods_update_call_receiver_bt);
        Button goodsUpdateBt = popupView.findViewById(R.id.goods_update_bt);
        Button goodsUpdateCloseBt = popupView.findViewById(R.id.goods_update_close_bt);



        goodsUpdateGoodsID.setText(goodsInformation.getGoodsCode());
        goodsUpdateType.setText(goodsInformation.getGoodsType());
        goodsUpdateSenderName.setText(goodsInformation.getGoodsSendName());
        goodsUpdateSenderPhone.setText(goodsInformation.getGoodsSendPhone());
        goodsUpdateSenderAddress.setText(goodsInformation.getGoodsSendProvince()+","+ goodsInformation.getGoodsSendDistrict() +","+goodsInformation.getGoodsSendCity());
        goodsUpdateSenderDate.setText(goodsInformation.getGoodsSendDate());
        goodsUpdateReceiveName.setText(goodsInformation.getGoodsReceiveName());
        goodsUpdateReceivePhone.setText(goodsInformation.getGoodsReceivePhone());
        goodsUpdateReceiveAddress.setText(goodsInformation.getGoodsReceiveProvince()+","+ goodsInformation.getGoodsReceiveDistrict() +","+goodsInformation.getGoodsReceiveCity());
        goodsUpdateReceiveDate.setText(goodsInformation.getGoodsReceiveDate());
        goodsUpdateGoodsQuantity.setText(goodsInformation.getGoodsQuantity());
        goodsUpdateMoney.setText(goodsInformation.getGoodsMoney());
        goodsUpdatePrice.setText("chua co data base");
        goodsUpdateNote.setText(goodsInformation.getGoodsNote());

        //parameter for spinner
        String[] goodsStsSelectList = new String[]{"Chưa Nhận","Đang Ở Văn Phòng","Đang Vận Chuyển","Đang Ở Kho","Đang Giao","Đã Hoàn Thành"};
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, goodsStsSelectList);
        goodsMainSts.setAdapter(adapterMonth);

        //init value for spinner
        if(goodsInformation.getGoodsSts().matches("100")){
            goodsMainSts.setSelection(0);
        } else if(goodsInformation.getGoodsSts().matches("200")) {
            goodsMainSts.setSelection(1);
        }else if(goodsInformation.getGoodsSts().matches("300")) {
            goodsMainSts.setSelection(2);
        }else if(goodsInformation.getGoodsSts().matches("400")) {
            goodsMainSts.setSelection(3);
        }else if(goodsInformation.getGoodsSts().matches("500")) {
            goodsMainSts.setSelection(4);
        }else if(goodsInformation.getGoodsSts().matches("600")) {
            goodsMainSts.setSelection(5);
        }

        // set selection value of spinner
        goodsMainSts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        goodsInformation.setGoodsSts("100");
                        break;
                    case 1:
                        goodsInformation.setGoodsSts("200");
                        break;
                    case 2:
                        goodsInformation.setGoodsSts("300");
                        break;
                    case 3:
                        goodsInformation.setGoodsSts("400");
                        break;
                    case 4:
                        goodsInformation.setGoodsSts("500");
                        break;
                    case 5:
                        goodsInformation.setGoodsSts("600");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodsInformation.setGoodsSts("100");
            }
        });







        goodsUpdateClearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsUpdateGoodsID.setText("");
                goodsUpdateType.setText("");
                goodsUpdateSenderName.setText("");
                goodsUpdateSenderPhone.setText("");
                goodsUpdateSenderAddress.setText("");
                goodsUpdateSenderDate.setText("");
                goodsUpdateReceiveName.setText("");
                goodsUpdateReceivePhone.setText("");
                goodsUpdateReceiveAddress.setText("");
                goodsUpdateReceiveDate.setText("");
                goodsUpdateGoodsQuantity.setText("");
                goodsUpdateMoney.setText("");
                goodsUpdatePrice.setText("");
                goodsUpdateNote.setText("");

            }
        });

        goodsUpdateReceiveCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        goodsUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(context);
                goodsInformation.setGoodsCode(goodsUpdateGoodsID.getText().toString());
                goodsInformation.setGoodsType(goodsUpdateType.getText().toString());
                goodsInformation.setGoodsSendName(goodsUpdateSenderName.getText().toString());
                goodsInformation.setGoodsSendPhone(goodsUpdateSenderPhone.getText().toString());
                goodsInformation.setGoodsSendCity(goodsUpdateSenderAddress.getText().toString()); // this one need to clarify, currently it not correct
                goodsInformation.setGoodsSendDate(goodsUpdateSenderDate.getText().toString());
                goodsInformation.setGoodsReceiveName(goodsUpdateReceiveName.getText().toString());
                goodsInformation.setGoodsReceivePhone(goodsUpdateReceivePhone.getText().toString());
                goodsInformation.setGoodsReceiveCity(goodsUpdateReceiveAddress.getText().toString()); // this one need to clarify, currently it is not correct
                goodsInformation.setGoodsReceiveDate(goodsUpdateReceiveDate.getText().toString());
                goodsInformation.setGoodsQuantity(goodsUpdateGoodsQuantity.getText().toString());
                goodsInformation.setGoodsMoney(goodsUpdateMoney.getText().toString());
                //missing price
                goodsInformation.setGoodsNote(goodsUpdateNote.getText().toString());

                goodsLocalDatabase.updateGoods(goodsInformation);

                Toast.makeText(context,"Đã Hoàn Thành Cập Nhật",Toast.LENGTH_SHORT).show();

            }
        });

        goodsUpdateCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        //show keyboard for EditText in popup windows
        popupWindow.setFocusable(true);
        popupWindow.update();




    }

    public static void goodsAddPopup(View view, final Context context, final Activity activity, final Fragment fragment) {
        final GoodsInformation goodsInformation = new GoodsInformation();
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //View popupView = inflater.inflate(R.layout.goods_update_all_infor, null);
        View popupView = LayoutInflater.from(context).inflate(R.layout.goods_add_new, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        final EditText goodsAddGoodsID = popupView.findViewById(R.id.goods_add_goods_id);
        final EditText goodsAddType = popupView.findViewById(R.id.goods_add_type);
        final EditText goodsAddSenderName = popupView.findViewById(R.id.goods_add_sender_name);
        final EditText goodsAddSenderPhone = popupView.findViewById(R.id.goods_add_sender_phone);
        final EditText goodsAddSenderAddress = popupView.findViewById(R.id.goods_add_sender_address);
        final EditText goodsAddSenderDate = popupView.findViewById(R.id.goods_add_sender_date);
        final EditText goodsAddReceiveName = popupView.findViewById(R.id.goods_add_receiver_name);
        final EditText goodsAddReceivePhone = popupView.findViewById(R.id.goods_add_receiver_phone);
        final EditText goodsAddReceiveAddress = popupView.findViewById(R.id.goods_add_receiver_address);
        final EditText goodsAddReceiveDate = popupView.findViewById(R.id.goods_add_receiver_date);
        Spinner goodsAddSts = popupView.findViewById(R.id.goods_add_sts);
        final EditText goodsAddGoodsQuantity = popupView.findViewById(R.id.goods_add_goods_quantity);
        final EditText goodsAddMoney = popupView.findViewById(R.id.goods_add_money);
        final EditText goodsAddPrice = popupView.findViewById(R.id.goods_add_goods_price);
        final EditText goodsAddNote = popupView.findViewById(R.id.goods_add_goods_notes);
        Button goodsAddClearBt = popupView.findViewById(R.id.goods_add_clear_bt);
        Button goodsAddBt = popupView.findViewById(R.id.goods_add_bt);
        Button goodsAddCloseBt = popupView.findViewById(R.id.goods_add_close_bt);
        Button goodsAddScanBt = popupView.findViewById(R.id.goods_add_scan_bt);


        //parameter for spinner
        spinderGoodsStatus(context,goodsAddSts);


        // set selection value of spinner
        goodsAddSts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        goodsInformation.setGoodsSts("100");
                        break;
                    case 1:
                        goodsInformation.setGoodsSts("200");
                        break;
                    case 2:
                        goodsInformation.setGoodsSts("300");
                        break;
                    case 3:
                        goodsInformation.setGoodsSts("400");
                        break;
                    case 4:
                        goodsInformation.setGoodsSts("500");
                        break;
                    case 5:
                        goodsInformation.setGoodsSts("600");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodsInformation.setGoodsSts("100");
            }
        });

        goodsAddClearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsAddGoodsID.setText("");
                goodsAddType.setText("");
                goodsAddSenderName.setText("");
                goodsAddSenderPhone.setText("");
                goodsAddSenderAddress.setText("");
                goodsAddSenderDate.setText("");
                goodsAddReceiveName.setText("");
                goodsAddReceivePhone.setText("");
                goodsAddReceiveAddress.setText("");
                goodsAddReceiveDate.setText("");
                goodsAddGoodsQuantity.setText("");
                goodsAddMoney.setText("");
                goodsAddPrice.setText("");
                goodsAddNote.setText("");
            }
        });


        goodsAddBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(context);
                goodsInformation.setGoodsCode(goodsAddGoodsID.getText().toString());
                goodsInformation.setGoodsType(goodsAddType.getText().toString());
                goodsInformation.setGoodsSendName(goodsAddSenderName.getText().toString());
                goodsInformation.setGoodsSendPhone(goodsAddSenderPhone.getText().toString());
                goodsInformation.setGoodsSendCity(goodsAddSenderAddress.getText().toString()); // this one need to clarify, currently it not correct
                goodsInformation.setGoodsSendDate(goodsAddSenderDate.getText().toString());
                goodsInformation.setGoodsReceiveName(goodsAddReceiveName.getText().toString());
                goodsInformation.setGoodsReceivePhone(goodsAddReceivePhone.getText().toString());
                goodsInformation.setGoodsReceiveCity(goodsAddReceiveAddress.getText().toString()); // this one need to clarify, currently it is not correct
                goodsInformation.setGoodsReceiveDate(goodsAddReceiveDate.getText().toString());
                goodsInformation.setGoodsQuantity(goodsAddGoodsQuantity.getText().toString());
                goodsInformation.setGoodsMoney(goodsAddMoney.getText().toString());
                //missing price
                goodsInformation.setGoodsNote(goodsAddNote.getText().toString());
                goodsLocalDatabase.addGoods(goodsInformation);

                Toast.makeText(context,"Đã Hoàn Thành Thêm mới",Toast.LENGTH_SHORT).show();

            }
        });

        goodsAddCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        goodsAddScanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.scanBarcode(activity,fragment);
            }
        });
        goodsAddGoodsID.setText(MainActivity.scanIDResult);

        //show keyboard for EditText in popup windows
        popupWindow.setFocusable(true);
        popupWindow.update();

    }

    public static void makeCall (Context context,String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if (item.getTitle() == "Chi Tiết") {

            goodsAllInforPopup(this.getWindow().getDecorView().getRootView(),this,goodsSelectFromListview);

        } else if(item.getTitle() == "Gọi Người Gửi") {

            makeCall(this,goodsSelectFromListview.getGoodsSendPhone());

        } else if(item.getTitle() == "Gọi Người Nhận") {
            makeCall(this,goodsSelectFromListview.getGoodsReceivePhone());

        }else if(item.getTitle() == "Cập Nhật") {
            goodsUpdateInforPopup(this.getWindow().getDecorView().getRootView(),this,goodsSelectFromListview);

        }else if(item.getTitle() == "Xóa") {
            GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(this);
            goodsLocalDatabase.deleteGoods(goodsSelectFromListview);
            Toast.makeText(this,"Đã Xóa Xong",Toast.LENGTH_SHORT).show();

        }else {return false;

        }

        return true;

    }

    // detect scrolling direction

    public static void visibleFloatButtonBaseScrollDirect(ListView listView){
        // detect scrolling direction

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int lastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lastFirstVisibleItem < firstVisibleItem) {
                    // scroll down -> hide the floating buttons
                    MainActivity.mainFab.setVisibility(View.GONE);
                    MainActivity.summaryFab.setVisibility(View.GONE);
                    MainActivity.goodsFab.setVisibility(View.GONE);
                    MainActivity.scanFab.setVisibility(View.GONE);
                    MainActivity.searchFab.setVisibility(View.GONE);
                    MainActivity.uploadFab.setVisibility(View.GONE);
                    MainActivity.downloadFab.setVisibility(View.GONE);
                }
                if (lastFirstVisibleItem > firstVisibleItem) {
                    // scroll up -> visible the floating buttons
                    MainActivity.mainFab.setVisibility(View.VISIBLE);
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    public static void scanBarcode(Activity activity, Fragment fragment){
        IntentIntegrator integrator = new IntentIntegrator(activity).forSupportFragment(fragment);

        //IntentIntegrator integrator = new IntentIntegrator(getActivity());
        // use forSupportFragment or forFragment method to use fragments instead of activity
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan Mã Hàng");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(Capture.class);
        integrator.initiateScan();
    }

    public static void spinderGoodsStatus (Context context,Spinner spinner){
        //parameter for spinner
        String[] goodsStsSelectList = new String[]{"Chưa Nhận","Đang Ở Văn Phòng","Đang Vận Chuyển","Đang Ở Kho","Đang Giao","Đã Hoàn Thành"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, goodsStsSelectList);
        spinner.setAdapter(adapter);

    }

    public static String getDeviceIMEI(Context context) {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
           // deviceUniqueIdentifier = tm.getImei();
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    public static String getDevicePhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }

    public static String getDeviceMacNumber(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return address;
    }

    public static void dateSelect(Context context, final EditText editText, final String dateFormat){

        //Set date listenning :
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // set format of date before set to edit text
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

                editText.setText(sdf.format(myCalendar.getTime()));


            }

        };

        //date selected popup windows
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();




    }

    public static void dateTimeSelect(final Context context, final EditText editText, final String dateFormat, final Boolean hoursView24H){


        //Set date listenning :
        final Calendar myCalendar = Calendar.getInstance();


        // after selected date the time will be selected -> popup for time dialog will show after finished date set
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                // set format of date before set to edit text
                DateFormat sdf = new SimpleDateFormat(dateFormat);
                editText.setText(sdf.format(myCalendar.getTime()));

            }
        };

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                new TimePickerDialog(context,time,Calendar.HOUR_OF_DAY,Calendar.MINUTE,hoursView24H).show();
            }

        };
        //date selected date popup windows
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    public static void timeSelect(final Context context, final EditText editText, final String dateFormat, final Boolean hoursView24H){


        //Set date listenning :
        final Calendar myCalendar = Calendar.getInstance();


        // after selected date the time will be selected -> popup for time dialog will show after finished date set
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                // set format of date before set to edit text
                DateFormat sdf = new SimpleDateFormat(dateFormat);
                editText.setText(sdf.format(myCalendar.getTime()));

            }
        };
        //date selected date popup windows
        new TimePickerDialog(context,time,Calendar.HOUR_OF_DAY,Calendar.MINUTE,hoursView24H).show();

    }

    public static void downloadData(Context context,List<List<Object>> listObj){
        if(listObj.size() <1){
            Toast.makeText(context,"Không Có Dữ Liệu để tải về",Toast.LENGTH_SHORT).show();
        }else {
            GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(context);
            goodsLocalDatabase.deleteAllData();
            if(goodsLocalDatabase.getGoodsCount()>0){
                Toast.makeText(context,"Quá Trình Cập Nhật Bị Lỗi, Hãy Thử Lại",Toast.LENGTH_SHORT).show();
            }else {
                for (int i =0;i<listObj.size();i++){
                    goodsLocalDatabase.addGoods(MainActivity.convert2LocalObj(listObj.get(i)));
                }
            }

        }
    }

    public static GoodsInformation convert2LocalObj(List<Object> object){

        String goodsCode = object.get(MainActivity.GOODS_CODE_MAP).toString();
        String goodsName = object.get(MainActivity.GOODS_NAME_MAP).toString();
        String goodsType = object.get(MainActivity.GOODS_TYPE_MAP).toString();
        String goodsSts = object.get(MainActivity.GOODS_STS_MAP).toString();
        String goodsQuantity = object.get(MainActivity.GOODS_QUANTITY_MAP).toString();
        String goodsUnit = object.get(MainActivity.GOODS_UNIT_MAP).toString();
        String goodsWeight = object.get(MainActivity.GOODS_WEIGHT_MAP).toString();
        String goodsMoney = object.get(MainActivity.GOODS_MONEY_MAP).toString();
        String goodsDate = object.get(MainActivity.GOODS_DATE_MAP).toString();
        String goodsLocation = object.get(MainActivity.GOODS_LOCATION_MAP).toString();
        String goodsNote = object.get(MainActivity.GOODS_NOTE_MAP).toString();

        String goodsSendName = object.get(MainActivity.GOODS_SENDNAME_MAP).toString();
        String goodsSendID = object.get(MainActivity.GOODS_SENDID_MAP).toString();
        String goodsSendPhone = object.get(MainActivity.GOODS_SENDPHONE_MAP).toString();
        String goodsSendCity = object.get(MainActivity.GOODS_SENDCITY_MAP).toString();
        String goodsSendDistrict = object.get(MainActivity.GOODS_SENDDISTRICT_MAP).toString();
        String goodsSendProvince = object.get(MainActivity.GOODS_SENDPROVINCE_MAP).toString();
        String goodsSendCalled = object.get(MainActivity.GOODS_SENDCALLED_MAP).toString();
        String goodsSendDate = object.get(MainActivity.GOODS_SENDDATE_MAP).toString();
        String goodsSendNote = object.get(MainActivity.GOODS_SENDNOTE_MAP).toString();

        String goodsReceiveName = object.get(MainActivity.GOODS_RECEIVENAME_MAP).toString();
        String goodsReceiveID = object.get(MainActivity.GOODS_RECEIVEID_MAP).toString();
        String goodsReceivePhone = object.get(MainActivity.GOODS_RECEIVEPHONE_MAP).toString();
        String goodsReceiveCity = object.get(MainActivity.GOODS_RECEIVECITY_MAP).toString();
        String goodsReceiveDistrict = object.get(MainActivity.GOODS_RECEIVEDISTRICT_MAP).toString();
        String goodsReceiveProvince = object.get(MainActivity.GOODS_RECEIVEPROVINCE_MAP).toString();
        String goodsReceiveCalled = object.get(MainActivity.GOODS_RECEIVECALLED_MAP).toString();
        String goodsReceiveDate = object.get(MainActivity.GOODS_RECEIVEDATE_MAP).toString();
        String goodsReceiveNote = object.get(MainActivity.GOODS_RECEIVENOTE_MAP).toString();

        GoodsInformation goodsInformation = new GoodsInformation(goodsCode, goodsName, goodsType, goodsSts, goodsQuantity, goodsUnit,
                goodsWeight, goodsMoney, goodsDate, goodsLocation, goodsNote, goodsSendName,
                goodsSendID, goodsSendPhone, goodsSendCity, goodsSendDistrict, goodsSendProvince,
                goodsSendCalled, goodsSendDate, goodsSendNote, goodsReceiveName, goodsReceiveID,
                goodsReceivePhone, goodsReceiveCity, goodsReceiveDistrict, goodsReceiveProvince, goodsReceiveCalled,
                goodsReceiveDate, goodsReceiveNote);
        return goodsInformation;
    }

    public static boolean uploadData2Server(Context context){

        // delete all currently data in server
        final GoogleSheetInterface googleSheetInterface = new GoogleSheetInterface();
        final boolean googleSheetResult =false;
        boolean result = false;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                googleSheetInterface.deleteAllData(MainActivity.DATABASE_SPREADSHEET_ID,MainActivity.DATABASE_SHEET_NAME,MainActivity.DATABASE_SHEET_RANGE,googleSheetResult);
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // upload database to googlesheet
        if(googleSheetResult){
            final List<List<Object>> listObj = new ArrayList<>();
            GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(context);
            List<GoodsInformation> allData = goodsLocalDatabase.getAllGoods();
            for (int i =0;i<allData.size();i++){
                List<Object> object = new ArrayList<>();
                object.add(allData.get(i).getGoodsCode());
                object.add(allData.get(i).getGoodsName());
                object.add(allData.get(i).getGoodsType());
                object.add(allData.get(i).getGoodsSts());
                object.add(allData.get(i).getGoodsQuantity());
                object.add(allData.get(i).getGoodsUnit());
                object.add(allData.get(i).getGoodsWeight());
                object.add(allData.get(i).getGoodsMoney());
                object.add(allData.get(i).getGoodsDate());
                object.add(allData.get(i).getGoodsLocation());
                object.add(allData.get(i).getGoodsNote());

                object.add(allData.get(i).getGoodsSendName());
                object.add(allData.get(i).getGoodsSendID());
                object.add(allData.get(i).getGoodsSendPhone());
                object.add(allData.get(i).getGoodsSendCity());
                object.add(allData.get(i).getGoodsSendDistrict());
                object.add(allData.get(i).getGoodsSendProvince());
                object.add(allData.get(i).getGoodsSendCalled());
                object.add(allData.get(i).getGoodsSendDate());
                object.add(allData.get(i).getGoodsSendNote());

                object.add(allData.get(i).getGoodsReceiveName());
                object.add(allData.get(i).getGoodsReceiveID());
                object.add(allData.get(i).getGoodsReceivePhone());
                object.add(allData.get(i).getGoodsReceiveCity());
                object.add(allData.get(i).getGoodsReceiveDistrict());
                object.add(allData.get(i).getGoodsReceiveProvince());
                object.add(allData.get(i).getGoodsReceiveCalled());
                object.add(allData.get(i).getGoodsReceiveDate());
                object.add(allData.get(i).getGoodsReceiveNote());

                listObj.add(object);
            }



            Runnable runnable01 = new Runnable() {
                @Override
                public void run() {
                    googleSheetInterface.updateRangeData(MainActivity.DATABASE_SPREADSHEET_ID,MainActivity.DATABASE_SHEET_NAME,MainActivity.LICENSE_SHEET_RANGE,listObj,googleSheetResult);
                }
            };
            Thread t1 = new Thread(runnable01);
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (googleSheetResult){
                result = true;
            }

        }
    return result;
    }

    public static boolean downloadDataFromServer(Context context) {
        final boolean googleSheetResult = false;
        boolean result = false;
        final GoogleSheetInterface googleSheetInterface = new GoogleSheetInterface();
        final GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(context);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<List<Object>> dataAll = googleSheetInterface.getData(MainActivity.DATABASE_SPREADSHEET_ID, MainActivity.DATABASE_SHEET_NAME, MainActivity.DATABASE_SHEET_RANGE);

                if(dataAll.size()>0){

                    goodsLocalDatabase.deleteAllData();
                    for (int i =0;i<dataAll.size();i++){
                        GoodsInformation goodsInformation = new GoodsInformation(dataAll.get(i).get(0).toString(),dataAll.get(i).get(1).toString(),dataAll.get(i).get(2).toString(),
                                dataAll.get(i).get(3).toString(),dataAll.get(i).get(4).toString(),dataAll.get(i).get(5).toString(),dataAll.get(i).get(6).toString(),dataAll.get(i).get(7).toString(),
                                dataAll.get(i).get(8).toString(),dataAll.get(i).get(9).toString(),dataAll.get(i).get(10).toString(),dataAll.get(i).get(11).toString(),dataAll.get(i).get(12).toString(),
                                dataAll.get(i).get(13).toString(),dataAll.get(i).get(14).toString(),dataAll.get(i).get(15).toString(),dataAll.get(i).get(16).toString(),dataAll.get(i).get(17).toString(),
                                dataAll.get(i).get(18).toString(),dataAll.get(i).get(19).toString(),dataAll.get(i).get(20).toString(),dataAll.get(i).get(21).toString(),dataAll.get(i).get(22).toString(),
                                dataAll.get(i).get(23).toString(),dataAll.get(i).get(24).toString(),dataAll.get(i).get(25).toString(),dataAll.get(i).get(26).toString(),
                                dataAll.get(i).get(27).toString(),dataAll.get(i).get(28).toString());
                        goodsLocalDatabase.addGoods(goodsInformation);
                    }

                }

            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
            result = true;
        } catch (InterruptedException e) {
            result =false;
            e.printStackTrace();
        }
    return result;
    }


}
