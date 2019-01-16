package com.example.easierhomefinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HomeFinances.db";
    public static final String HF_TABLE_NAME = "HomeFinance";
    public static final String HF_COLUMN_ID = "id";
    public static final String HF_COLUMN_DATE = "date";
    public static final String HF_COLUMN_INCOME = "income";
    public static final String HF_COLUMN_EXPENSE = "expense";
    public static final String HF_COLUMN_SAVE = "save";
    public static final String HF_COLUMN_AMOUNT = "amount";

    public DBHelper(Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table HomeFinance (id integer primary key autoincrement,date text,income integer, expense integer,save integer,amount unsignedinteger)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS HomeFinance");
        onCreate(db);
    }

    public boolean insertHistory(String date, int income, int expense, int save, int amount){

        //Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt1"+date+" bools : "+income+" "+expense+" "+ save+" "+amount);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date",date);
        contentValues.put("income",income);
        contentValues.put("expense",expense);
        contentValues.put("save",save);
        contentValues.put("amount",amount);

        db.insert("HomeFinance",null,contentValues);
        //Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt2");
        return true;
    }

    public Cursor getDatabyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from HomeFinance where id="+id+"",null);
        return res;
    }

    public ArrayList getDatabydate(String date){
        /* db=this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from HomeFinance where date ="+date+"",null);


        return res;*/
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from HomeFinance",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            //Log.e(this.getClass().getName(),res.getString(res.getColumnIndex(HF_COLUMN_DATE))+"compare~"+date);
            if (res.getString(res.getColumnIndex(HF_COLUMN_DATE)).equals(date)) {
                arrayList.add(res);
                //Log.e(this.getClass().getName(), res.getString(res.getColumnIndex(DBHelper.HF_COLUMN_AMOUNT))+"?>???????");
            }
            res.moveToNext();
        }
        return arrayList;   //커서들의 배열을 반환하도록
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int)DatabaseUtils.queryNumEntries(db,HF_TABLE_NAME);

        return numRows;
    }

    public boolean updateHistory(Integer id, String date, boolean income, boolean expense, boolean save, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("income",income);
        contentValues.put("expense",expense);
        contentValues.put("save",save);
        contentValues.put("amount",amount);
        db.update("HomeFinance", contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteHistory(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("HomeFinance","id=?",new String[]{Integer.toString(id)});
    }

    public ArrayList getAllHistory(String param){
        //if (rs.getInt(rs.getColumnIndex(DBHelper.HF_COLUMN_INCOME)) == param) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from HomeFinance",null);
        res.moveToFirst();

        while (res.isAfterLast()==false){
            if (res.getInt(res.getColumnIndex(param)) == 1) {   // parameter로 전달된 이름의 field가 1인 데이터행의
                arrayList.add(res.getString(res.getColumnIndex(HF_COLUMN_AMOUNT))); // 금액 field를 추가해라
            }
            res.moveToNext();
        }
        return arrayList;
    }
}
