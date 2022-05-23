package com.example.logistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
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


    //floating button for main controll buttons
    static public FloatingActionButton mainFab;
    static public FloatingActionButton summaryFab;
    static public FloatingActionButton goodsFab;
    static public FloatingActionButton scanFab;
    static public FloatingActionButton searchFab;
    static public FloatingActionButton uploadFab;
    static public FloatingActionButton downloadFab;

    // table ID for hàng hóa

    //Fragment
    static public ViewPager mainViewPager;

    //Context menu and fragment active detect
    //1 -Main; 2 - GoodsFragment; 3 - GoodsSearchFragment; 4-GoodsScanFragment
    static public Integer curActiveFragment =1;
    static public GoodsInformation goodsSelectFromListview = new GoodsInformation();


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
        mainFab =findViewById(R.id.main_fab);
        summaryFab =findViewById(R.id.summary_fab);
        goodsFab =findViewById(R.id.goods_fab);
        scanFab =findViewById(R.id.scan_fab);
        searchFab =findViewById(R.id.search_fab);
        uploadFab =findViewById(R.id.upload_fab);
        downloadFab =findViewById(R.id.download_fab);

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

        searchFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(2);
                summaryFab.setVisibility(View.GONE);
                goodsFab.setVisibility(View.GONE);
                scanFab.setVisibility(View.GONE);
                searchFab.setVisibility(View.GONE);
                uploadFab.setVisibility(View.GONE);
                downloadFab.setVisibility(View.GONE);
            }
        });

        scanFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainViewPager.setCurrentItem(3);
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
        adapter.addFragment(new GoodsScanFragment(),"Goods Scan");

        mainViewPager.setAdapter(adapter);
    }


    public static void goodsAllInforPopup(View view, final Context context, final GoodsInformation goodsInformation) {

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

            }
        });

        goodsMainCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });




    }


    public void goodsUpdateInforPopup(View view, final Context context, final GoodsInformation goodsInformation) {

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
        EditText goodsUpdatePrice = popupView.findViewById(R.id.goods_update_goods_price);
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
                popupWindow.dismiss();

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

        }else {return false;

        }

        return true;

    }

}
