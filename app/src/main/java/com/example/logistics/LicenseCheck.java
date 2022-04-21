package com.example.logistics;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LicenseCheck extends Activity {
    static public final String LICENSE_SHARED_PREFS_NAME ="license";
    static public final String LICENSE_SHARED_PREFS_ID ="licenseID";
    static public final String LICENSE_SHARED_PREFS_EXP ="licenseExpireDate";


    public void licenseStatusUpdate() throws ParseException {
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


    public void licenseLocalSaveData(String id, String expDate){
        SharedPreferences sharedPreferences =getSharedPreferences(LICENSE_SHARED_PREFS_NAME,getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(LICENSE_SHARED_PREFS_ID,id);
        editor.putString(LICENSE_SHARED_PREFS_EXP,expDate);
        editor.apply();

    }

    public void licenseLocalRetriveData(String id, String expDate){
        SharedPreferences sharedPreferences =getSharedPreferences(LICENSE_SHARED_PREFS_NAME,getApplicationContext().MODE_PRIVATE);
        id=sharedPreferences.getString(LICENSE_SHARED_PREFS_ID,"");
        expDate = sharedPreferences.getString(LICENSE_SHARED_PREFS_EXP,"");

    }
}

