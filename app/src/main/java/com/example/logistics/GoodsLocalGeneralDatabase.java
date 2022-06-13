package com.example.logistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GoodsLocalGeneralDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GENERAL_INFOR";

    // Table name: Note.
    private static final String TABLE_NOTE = "GENERAL_SETTING";

    private static final String COLUMN_ID = "Column_ID";
    private static final String COLUMN_01 = "Column01";
    private static final String COLUMN_02 = "Column02";
    private static final String COLUMN_03 = "Column03";
    private static final String COLUMN_04 = "Column04";
    private static final String COLUMN_05 = "Column05";
    private static final String COLUMN_06 = "Column06";
    private static final String COLUMN_07 = "Column07";
    private static final String COLUMN_08 = "Column08";
    private static final String COLUMN_09 = "Column09";
    private static final String COLUMN_10 = "Column10";

    public GoodsLocalGeneralDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        // have to set primary key to autoincrement to avoid dupplicate when add new one
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"+ COLUMN_01 + " TEXT,"
                + COLUMN_02 + " TEXT,"+ COLUMN_03 + " TEXT,"+ COLUMN_04 + " TEXT,"
                + COLUMN_05 + " TEXT,"+ COLUMN_06 + " TEXT,"+ COLUMN_07 + " TEXT,"
                + COLUMN_08 + " TEXT," + COLUMN_09 + " TEXT," + COLUMN_10 + " TEXT"  + ")";

        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.


    public void addInfor (GeneralInformation generalInformation)
    {
        Log.i(TAG, "MyDatabaseHelper.addCus ... " );

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        // not add the ID (primary key) to avoid dupplicate ID -> let blank then it will automatic generate by SQLLite
       // value.put(COLUMN_GOODS_ID,goods.getGoodsID());
        value.put(COLUMN_01,generalInformation.getColumn01());
        value.put(COLUMN_02,generalInformation.getColumn02());
        value.put(COLUMN_03,generalInformation.getColumn03());
        value.put(COLUMN_04,generalInformation.getColumn04());
        value.put(COLUMN_05,generalInformation.getColumn05());
        value.put(COLUMN_06,generalInformation.getColumn06());
        value.put(COLUMN_07,generalInformation.getColumn07());
        value.put(COLUMN_08,generalInformation.getColumn08());
        value.put(COLUMN_09,generalInformation.getColumn09());
        value.put(COLUMN_10,generalInformation.getColumn10());

        // Inserting Row
        db.insert(TABLE_NOTE, null, value);
        // Closing database connection
        db.close();
    }

    public GeneralInformation getInforBaseID ( int id)  // search full match
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();
        GeneralInformation infor = new GeneralInformation();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_ID,COLUMN_01,COLUMN_02,COLUMN_03,COLUMN_04,COLUMN_05,COLUMN_06,COLUMN_07,COLUMN_08,
                        COLUMN_09,COLUMN_10}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor != null && cursor.moveToFirst()) {

            infor.setId(Integer.parseInt(cursor.getString(0)));
            infor.setColumn01(cursor.getString(1));
            infor.setColumn02(cursor.getString(2));
            infor.setColumn03(cursor.getString(3));
            infor.setColumn04(cursor.getString(4));
            infor.setColumn05(cursor.getString(5));
            infor.setColumn06(cursor.getString(6));
            infor.setColumn07(cursor.getString(7));
            infor.setColumn08(cursor.getString(8));
            infor.setColumn09(cursor.getString(9));
            infor.setColumn10(cursor.getString(10));

            cursor.close();
        } else {
            infor =null;
        }
        return infor;
    }


    public List<GeneralInformation> getAllInfor () {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<GeneralInformation> inforList = new ArrayList<GeneralInformation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GeneralInformation infor = new GeneralInformation();

                infor.setId(Integer.parseInt(cursor.getString(0)));
                infor.setColumn01(cursor.getString(1));
                infor.setColumn02(cursor.getString(2));
                infor.setColumn03(cursor.getString(3));
                infor.setColumn04(cursor.getString(4));
                infor.setColumn05(cursor.getString(5));
                infor.setColumn06(cursor.getString(6));
                infor.setColumn07(cursor.getString(7));
                infor.setColumn08(cursor.getString(8));
                infor.setColumn09(cursor.getString(9));
                infor.setColumn10(cursor.getString(10));

                // Adding note to list
                inforList.add(infor);
            } while (cursor.moveToNext());
        }

        // return note list
        return inforList;
    }

    public int getInforCount () {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count1 = cursor.getCount();

        cursor.close();

        // return count
        return count1;
    }

    public int updateInfor (GeneralInformation infor){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();

        value.put(COLUMN_ID,infor.getId());
        value.put(COLUMN_01,infor.getColumn01());
        value.put(COLUMN_02,infor.getColumn02());
        value.put(COLUMN_03,infor.getColumn03());
        value.put(COLUMN_04,infor.getColumn04());
        value.put(COLUMN_05,infor.getColumn05());
        value.put(COLUMN_06,infor.getColumn06());
        value.put(COLUMN_07,infor.getColumn07());
        value.put(COLUMN_08,infor.getColumn08());
        value.put(COLUMN_09,infor.getColumn09());
        value.put(COLUMN_10,infor.getColumn10());

        // updating row
        return db.update(TABLE_NOTE, value, COLUMN_ID + " = ?",
                new String[]{String.valueOf(infor.getId())});
    }

    public void deleteGoods (GeneralInformation infor){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(infor.getId())});
        db.close();
    }

    public void deleteAllData(){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NOTE);
        db.close();
    }
}
