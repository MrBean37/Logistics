package com.example.logistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GoodsLocalDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HANG_HOA";

    // Table name: Note.
    private static final String TABLE_NOTE = "THONG_TIN_HANG";

    private static final String COLUMN_GOODS_ID = "Goods_ID";
    private static final String COLUMN_GOODS_CODE = "Goods_Code";
    private static final String COLUMN_GOODS_NAME = "Goods_Name";
    private static final String COLUMN_GOODS_TYPE = "Goods_Type";
    private static final String COLUMN_GOODS_STS = "Goods_Sts";
    private static final String COLUMN_GOODS_QUANTITY = "Goods_Quantity";
    private static final String COLUMN_GOODS_UNIT = "Goods_Unit";
    private static final String COLUMN_GOODS_WEIGHT = "Goods_Weight";
    private static final String COLUMN_GOODS_MONEY = "Goods_Money";
    private static final String COLUMN_GOODS_DATE = "Goods_Date";
    private static final String COLUMN_GOODS_LOCATION = "Goods_Location";
    private static final String COLUMN_GOODS_NOTE = "Goods_Note";

    private static final String COLUMN_GOODS_SEND_NAME = "Goods_Send_Name";
    private static final String COLUMN_GOODS_SEND_ID = "Goods_Send_ID";
    private static final String COLUMN_GOODS_SEND_PHONE = "Goods_Send_Phone";
    private static final String COLUMN_GOODS_SEND_CITY = "Goods_Send_City";
    private static final String COLUMN_GOODS_SEND_DISTRICT = "Goods_Send_District";
    private static final String COLUMN_GOODS_SEND_PROVINCE = "Goods_Send_Province";
    private static final String COLUMN_GOODS_SEND_CALLED = "Goods_Send_Called";
    private static final String COLUMN_GOODS_SEND_DATE = "Goods_Send_Date";
    private static final String COLUMN_GOODS_SEND_NOTE ="Goods_Send_Note";

    private static final String COLUMN_GOODS_RECEIVE_NAME = "Goods_Receive_Name";
    private static final String COLUMN_GOODS_RECEIVE_ID = "Goods_Receive_ID";
    private static final String COLUMN_GOODS_RECEIVE_PHONE = "Goods_Receive_Phone";
    private static final String COLUMN_GOODS_RECEIVE_CITY = "Goods_Receive_City";
    private static final String COLUMN_GOODS_RECEIVE_DISTRICT = "Goods_Receive_District";
    private static final String COLUMN_GOODS_RECEIVE_PROVICE = "Goods_Receive_Provice";
    private static final String COLUMN_GOODS_RECEIVE_CALLED = "Goods_Receive_Called";
    private static final String COLUMN_GOODS_RECEIVE_DATE = "Goods_Receive_Date";
    private static final String COLUMN_GOODS_RECEIVE_NOTE ="Goods_Receive_Note";



    public GoodsLocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        // have to set primary key to autoincrement to avoid dupplicate when add new one
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_GOODS_ID + " INTEGER PRIMARY KEY,"+ COLUMN_GOODS_CODE + " TEXT,"
                + COLUMN_GOODS_NAME + " TEXT,"+ COLUMN_GOODS_TYPE + " TEXT,"+ COLUMN_GOODS_STS + " TEXT,"
                + COLUMN_GOODS_QUANTITY + " TEXT,"+ COLUMN_GOODS_UNIT + " TEXT,"+ COLUMN_GOODS_WEIGHT + " TEXT,"
                + COLUMN_GOODS_MONEY + " TEXT," + COLUMN_GOODS_DATE + " TEXT," + COLUMN_GOODS_LOCATION + " TEXT," + COLUMN_GOODS_NOTE + " TEXT,"+ COLUMN_GOODS_SEND_NAME + " TEXT," + COLUMN_GOODS_SEND_ID + " TEXT,"+ COLUMN_GOODS_SEND_PHONE + " TEXT,"
                + COLUMN_GOODS_SEND_CITY + " TEXT,"+ COLUMN_GOODS_SEND_DISTRICT + " TEXT,"+ COLUMN_GOODS_SEND_PROVINCE + " TEXT,"
                + COLUMN_GOODS_SEND_CALLED + " TEXT," + COLUMN_GOODS_SEND_DATE + " TEXT," + COLUMN_GOODS_SEND_NOTE + " TEXT,"+ COLUMN_GOODS_RECEIVE_NAME + " TEXT," + COLUMN_GOODS_RECEIVE_ID + " TEXT,"
                + COLUMN_GOODS_RECEIVE_PHONE + " TEXT,"+ COLUMN_GOODS_RECEIVE_CITY + " TEXT,"+ COLUMN_GOODS_RECEIVE_DISTRICT + " TEXT,"
                + COLUMN_GOODS_RECEIVE_PROVICE + " TEXT,"+ COLUMN_GOODS_RECEIVE_CALLED + " TEXT," + COLUMN_GOODS_RECEIVE_DATE + " TEXT," + COLUMN_GOODS_RECEIVE_NOTE + " TEXT" + ")";

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
    public void createDefaultDatabase() {
        int count = this.getGoodsCount();
        GoodsInformation goods;
        if (count == 0) {
            //Create general information:

            for (int i = -1; i < 40; i++) {
                String ten_goods = "Hàng số " + i;
                goods = new GoodsInformation();
                goods.setGoodsID(i);
                goods.setGoodsCode(ten_goods +"Code");
                goods.setGoodsMoney(ten_goods + "Money");
                goods.setGoodsName(ten_goods + "Name");
                goods.setGoodsQuantity(ten_goods + "Quantity");
                goods.setGoodsSts(ten_goods + "Sts");
                goods.setGoodsType(ten_goods + "type");
                goods.setGoodsUnit(ten_goods + "Unit");
                goods.setGoodsWeight(ten_goods + "Weight");
                goods.setGoodsDate(ten_goods + "Date");
                goods.setGoodsLocation(ten_goods + "Location");
                goods.setGoodsNote(ten_goods + "Note");

                goods.setGoodsReceiveName(ten_goods + "Receive Name");
                goods.setGoodsReceiveID(ten_goods + "Receive ID");
                goods.setGoodsReceiveDistrict(ten_goods + "Receive District");
                goods.setGoodsReceiveCity(ten_goods + "Receive City");
                goods.setGoodsReceiveProvince(ten_goods + "Receive Province");
                goods.setGoodsReceivePhone(ten_goods + "Receive Phone");
                goods.setGoodsReceiveCalled(ten_goods + "Receive Called");
                goods.setGoodsReceiveNote(ten_goods + "Receive Note");
                goods.setGoodsReceiveDate(ten_goods + "Receive Date");

                goods.setGoodsSendName(ten_goods + "Send Name");
                goods.setGoodsSendID(ten_goods + "Send ID");
                goods.setGoodsSendDistrict(ten_goods + "Send District");
                goods.setGoodsSendCity(ten_goods + "Send City");
                goods.setGoodsSendProvince(ten_goods + "Send Province");
                goods.setGoodsSendPhone(ten_goods + "Send Phone");
                goods.setGoodsSendCalled(ten_goods + "Send Called");
                goods.setGoodsSendNote(ten_goods + "Send Note");
                goods.setGoodsSendDate(ten_goods + "Send Date");


                this.addGoods(goods);


            }
        }
    }


    public void addGoods (GoodsInformation goods)
    {
        Log.i(TAG, "MyDatabaseHelper.addCus ... " + goods.getGoodsName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        // not add the ID (primary key) to avoid dupplicate ID -> let blank then it will automatic generate by SQLLite
       // value.put(COLUMN_GOODS_ID,goods.getGoodsID());
        value.put(COLUMN_GOODS_CODE,goods.getGoodsCode());
        value.put(COLUMN_GOODS_NAME,goods.getGoodsName());
        value.put(COLUMN_GOODS_TYPE,goods.getGoodsType());
        value.put(COLUMN_GOODS_STS,goods.getGoodsSts());
        value.put(COLUMN_GOODS_QUANTITY,goods.getGoodsQuantity());
        value.put(COLUMN_GOODS_UNIT,goods.getGoodsUnit());
        value.put(COLUMN_GOODS_WEIGHT,goods.getGoodsWeight());
        value.put(COLUMN_GOODS_MONEY,goods.getGoodsMoney());
        value.put(COLUMN_GOODS_DATE,goods.getGoodsDate());
        value.put(COLUMN_GOODS_LOCATION,goods.getGoodsLocation());
        value.put(COLUMN_GOODS_NOTE,goods.getGoodsNote());


        value.put(COLUMN_GOODS_SEND_NAME,goods.getGoodsSendName());
        value.put(COLUMN_GOODS_SEND_ID,goods.getGoodsSendID());
        value.put(COLUMN_GOODS_SEND_PHONE,goods.getGoodsSendPhone());
        value.put(COLUMN_GOODS_SEND_CITY,goods.getGoodsSendCity());
        value.put(COLUMN_GOODS_SEND_DISTRICT,goods.getGoodsSendDistrict());
        value.put(COLUMN_GOODS_SEND_PROVINCE,goods.getGoodsSendProvince());
        value.put(COLUMN_GOODS_SEND_CALLED,goods.getGoodsSendCalled());
        value.put(COLUMN_GOODS_SEND_DATE,goods.getGoodsSendDate());
        value.put(COLUMN_GOODS_SEND_NOTE,goods.getGoodsSendNote());


        value.put(COLUMN_GOODS_RECEIVE_NAME,goods.getGoodsReceiveName());
        value.put(COLUMN_GOODS_RECEIVE_ID,goods.getGoodsReceiveID());
        value.put(COLUMN_GOODS_RECEIVE_PHONE,goods.getGoodsReceivePhone());
        value.put(COLUMN_GOODS_RECEIVE_CITY,goods.getGoodsReceiveCity());
        value.put(COLUMN_GOODS_RECEIVE_DISTRICT,goods.getGoodsReceiveDistrict());
        value.put(COLUMN_GOODS_RECEIVE_PROVICE,goods.getGoodsReceiveProvince());
        value.put(COLUMN_GOODS_RECEIVE_CALLED,goods.getGoodsReceiveCalled());
        value.put(COLUMN_GOODS_RECEIVE_DATE,goods.getGoodsReceiveDate());
        value.put(COLUMN_GOODS_RECEIVE_NOTE,goods.getGoodsReceiveNote());


        // Inserting Row
        db.insert(TABLE_NOTE, null, value);
        // Closing database connection
        db.close();
    }

    public GoodsInformation getGoodsBaseID ( int id)  // search full match
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();
        GoodsInformation goods = new GoodsInformation();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor != null && cursor.moveToFirst()) {

            goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
            goods.setGoodsCode(cursor.getString(1));
            goods.setGoodsName(cursor.getString(2));
            goods.setGoodsType(cursor.getString(3));
            goods.setGoodsSts(cursor.getString(4));
            goods.setGoodsQuantity(cursor.getString(5));
            goods.setGoodsUnit(cursor.getString(6));
            goods.setGoodsWeight(cursor.getString(7));
            goods.setGoodsMoney(cursor.getString(8));
            goods.setGoodsDate(cursor.getString(9));
            goods.setGoodsLocation(cursor.getString(10));
            goods.setGoodsNote(cursor.getString(11));

            goods.setGoodsSendName(cursor.getString(12));
            goods.setGoodsSendID(cursor.getString(13));
            goods.setGoodsSendPhone(cursor.getString(14));
            goods.setGoodsSendCity(cursor.getString(15));
            goods.setGoodsSendDistrict(cursor.getString(16));
            goods.setGoodsSendProvince(cursor.getString(17));
            goods.setGoodsSendCalled(cursor.getString(18));
            goods.setGoodsSendDate(cursor.getString(19));
            goods.setGoodsSendNote(cursor.getString(20));

            goods.setGoodsReceiveName(cursor.getString(21));
            goods.setGoodsReceiveID(cursor.getString(22));
            goods.setGoodsReceivePhone(cursor.getString(23));
            goods.setGoodsReceiveCity(cursor.getString(24));
            goods.setGoodsReceiveDistrict(cursor.getString(25));
            goods.setGoodsReceiveProvince(cursor.getString(26));
            goods.setGoodsReceiveCalled(cursor.getString(27));
            goods.setGoodsReceiveDate(cursor.getString(28));
            goods.setGoodsReceiveNote(cursor.getString(29));

            cursor.close();
        } else {
            goods =null;
        }
        return goods;
    }

    // get customer information base on mobile phone number
    public List<GoodsInformation> getGoodsBaseReceivePhone ( String receivePhone)  // search contain
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +receivePhone);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_RECEIVE_PHONE + " LIKE ?",
                new String[]{"%" + String.valueOf(receivePhone) + "%"}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseSendPhone ( String sendPhone)   // search conatain
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +sendPhone);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_SEND_PHONE + " LIKE ?",
                new String[]{"%" + String.valueOf(sendPhone) + "%"}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseCode ( String code)   // search full match
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +code);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_CODE + "=?",
                new String[]{String.valueOf(code)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseSendName ( String sendName)   // search contain
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +sendName);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_SEND_NAME + " LIKE ?",
                new String[]{"%" + String.valueOf(sendName) + "%"}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor != null & cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseReceiveName ( String receiveName)  // search contain
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +receiveName);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_RECEIVE_NAME + " LIKE ?",
                new String[]{"%" + String.valueOf(receiveName) + "%"}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseSts ( String sts)  // search full match
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +sts);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_STS + "=?",
                new String[]{String.valueOf(sts)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (cursor != null & cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getGoodsBaseLocation ( String location) // search full match
    {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " +location);

        SQLiteDatabase db = this.getReadableDatabase();
        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_GOODS_ID,COLUMN_GOODS_CODE,COLUMN_GOODS_NAME,
                        COLUMN_GOODS_TYPE,COLUMN_GOODS_STS,COLUMN_GOODS_QUANTITY,COLUMN_GOODS_UNIT,COLUMN_GOODS_WEIGHT,COLUMN_GOODS_MONEY,COLUMN_GOODS_DATE,COLUMN_GOODS_LOCATION,COLUMN_GOODS_NOTE,
                        COLUMN_GOODS_SEND_NAME,COLUMN_GOODS_SEND_ID,COLUMN_GOODS_SEND_PHONE,COLUMN_GOODS_SEND_CITY,COLUMN_GOODS_SEND_DISTRICT,COLUMN_GOODS_SEND_PROVINCE,
                        COLUMN_GOODS_SEND_CALLED,COLUMN_GOODS_SEND_DATE,COLUMN_GOODS_SEND_NOTE,COLUMN_GOODS_RECEIVE_NAME,COLUMN_GOODS_RECEIVE_ID,COLUMN_GOODS_RECEIVE_PHONE,COLUMN_GOODS_RECEIVE_CITY,
                        COLUMN_GOODS_RECEIVE_DISTRICT,COLUMN_GOODS_RECEIVE_PROVICE,COLUMN_GOODS_RECEIVE_CALLED,COLUMN_GOODS_RECEIVE_DATE,COLUMN_GOODS_RECEIVE_NOTE}, COLUMN_GOODS_LOCATION + "=?",
                new String[]{String.valueOf(location)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public List<GoodsInformation> getAllGoods () {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<GoodsInformation> goodsList = new ArrayList<GoodsInformation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GoodsInformation goods = new GoodsInformation();

                goods.setGoodsID(Integer.parseInt(cursor.getString(0)));
                goods.setGoodsCode(cursor.getString(1));
                goods.setGoodsName(cursor.getString(2));
                goods.setGoodsType(cursor.getString(3));
                goods.setGoodsSts(cursor.getString(4));
                goods.setGoodsQuantity(cursor.getString(5));
                goods.setGoodsUnit(cursor.getString(6));
                goods.setGoodsWeight(cursor.getString(7));
                goods.setGoodsMoney(cursor.getString(8));
                goods.setGoodsDate(cursor.getString(9));
                goods.setGoodsLocation(cursor.getString(10));
                goods.setGoodsNote(cursor.getString(11));

                goods.setGoodsSendName(cursor.getString(12));
                goods.setGoodsSendID(cursor.getString(13));
                goods.setGoodsSendPhone(cursor.getString(14));
                goods.setGoodsSendCity(cursor.getString(15));
                goods.setGoodsSendDistrict(cursor.getString(16));
                goods.setGoodsSendProvince(cursor.getString(17));
                goods.setGoodsSendCalled(cursor.getString(18));
                goods.setGoodsSendDate(cursor.getString(19));
                goods.setGoodsSendNote(cursor.getString(20));

                goods.setGoodsReceiveName(cursor.getString(21));
                goods.setGoodsReceiveID(cursor.getString(22));
                goods.setGoodsReceivePhone(cursor.getString(23));
                goods.setGoodsReceiveCity(cursor.getString(24));
                goods.setGoodsReceiveDistrict(cursor.getString(25));
                goods.setGoodsReceiveProvince(cursor.getString(26));
                goods.setGoodsReceiveCalled(cursor.getString(27));
                goods.setGoodsReceiveDate(cursor.getString(28));
                goods.setGoodsReceiveNote(cursor.getString(29));

                // Adding note to list
                goodsList.add(goods);
            } while (cursor.moveToNext());
        }

        // return note list
        return goodsList;
    }

    public int getGoodsCount () {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count1 = cursor.getCount();

        cursor.close();

        // return count
        return count1;
    }

    public int updateGoods (GoodsInformation goods){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();

        value.put(COLUMN_GOODS_ID,goods.getGoodsID());
        value.put(COLUMN_GOODS_CODE,goods.getGoodsCode());
        value.put(COLUMN_GOODS_NAME,goods.getGoodsName());
        value.put(COLUMN_GOODS_TYPE,goods.getGoodsType());
        value.put(COLUMN_GOODS_STS,goods.getGoodsSts());
        value.put(COLUMN_GOODS_QUANTITY,goods.getGoodsQuantity());
        value.put(COLUMN_GOODS_UNIT,goods.getGoodsUnit());
        value.put(COLUMN_GOODS_WEIGHT,goods.getGoodsWeight());
        value.put(COLUMN_GOODS_MONEY,goods.getGoodsMoney());
        value.put(COLUMN_GOODS_DATE,goods.getGoodsDate());
        value.put(COLUMN_GOODS_LOCATION,goods.getGoodsLocation());
        value.put(COLUMN_GOODS_NOTE,goods.getGoodsNote());


        value.put(COLUMN_GOODS_SEND_NAME,goods.getGoodsSendName());
        value.put(COLUMN_GOODS_SEND_ID,goods.getGoodsSendID());
        value.put(COLUMN_GOODS_SEND_PHONE,goods.getGoodsSendPhone());
        value.put(COLUMN_GOODS_SEND_CITY,goods.getGoodsSendCity());
        value.put(COLUMN_GOODS_SEND_DISTRICT,goods.getGoodsSendDistrict());
        value.put(COLUMN_GOODS_SEND_PROVINCE,goods.getGoodsSendProvince());
        value.put(COLUMN_GOODS_SEND_CALLED,goods.getGoodsSendCalled());
        value.put(COLUMN_GOODS_SEND_DATE,goods.getGoodsSendDate());
        value.put(COLUMN_GOODS_SEND_NOTE,goods.getGoodsSendNote());


        value.put(COLUMN_GOODS_RECEIVE_NAME,goods.getGoodsReceiveName());
        value.put(COLUMN_GOODS_RECEIVE_ID,goods.getGoodsReceiveID());
        value.put(COLUMN_GOODS_RECEIVE_PHONE,goods.getGoodsReceivePhone());
        value.put(COLUMN_GOODS_RECEIVE_CITY,goods.getGoodsReceiveCity());
        value.put(COLUMN_GOODS_RECEIVE_DISTRICT,goods.getGoodsReceiveDistrict());
        value.put(COLUMN_GOODS_RECEIVE_PROVICE,goods.getGoodsReceiveProvince());
        value.put(COLUMN_GOODS_RECEIVE_CALLED,goods.getGoodsReceiveCalled());
        value.put(COLUMN_GOODS_RECEIVE_DATE,goods.getGoodsReceiveDate());
        value.put(COLUMN_GOODS_RECEIVE_NOTE,goods.getGoodsReceiveNote());
        // updating row
        return db.update(TABLE_NOTE, value, COLUMN_GOODS_ID + " = ?",
                new String[]{String.valueOf(goods.getGoodsID())});
    }

    public void deleteGoods (GoodsInformation goods){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_GOODS_ID + " = ?",
                new String[]{String.valueOf(goods.getGoodsID())});
        db.close();
    }

    public void deleteAllData(){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NOTE);
        db.close();
    }
}
