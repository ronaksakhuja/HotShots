package com.sodevan.hotshots.Databases;

/**
 * Created by ronaksakhuja on 03/03/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ronaksakhuja on 13/02/17.
 */

public class DatabaseHandler  {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "ChatThread";

    public static final String TABLE_NAME = "chats";

    //Column names
    public static final String SNO="serial";
    public static final String TEXT="text";
    public static final String PEOPLE="people";
    public static final String SCREENSHOT="screenshot";
    public static final String TIMESTAMP="timestamp";

    public static final String Q_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + SNO+" INTEGER PRIMARY KEY, " + TEXT + " TEXT, "+PEOPLE + " TEXT, "+TIMESTAMP+" TEXT, "+SCREENSHOT+" TEXT)";
    Context context;
    SQLiteDatabase database;
    public DatabaseHandler(Context c){this.context=c;}

    public DatabaseHandler open(){
        DBHelper dbh=new DBHelper(context);
        database=dbh.getWritableDatabase();
        return this;
    }

    public void Write(String text,String people,String ss,String timestamp){
        ContentValues cv = new ContentValues();
        cv.put(TEXT,text);
        cv.put(PEOPLE,people);
        cv.put(SCREENSHOT,ss);
        cv.put(TIMESTAMP,timestamp);
        database.insert(TABLE_NAME,null,cv);
    }


    public void close(){
        database.close();
    }


    public String read(){
        String result=null;
        String[] cols={SNO,TEXT,PEOPLE,SCREENSHOT,TIMESTAMP};
        Cursor cur = database.query(TABLE_NAME, cols, null, null, null, null, null);
        int iSNO=cur.getColumnIndex(SNO);
        int iTEXT=cur.getColumnIndex(TEXT);
        int iPeople=cur.getColumnIndex(PEOPLE);
        int iScreenshot=cur.getColumnIndex(SCREENSHOT);
        int iTime=cur.getColumnIndex(TIMESTAMP);
        for (cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
            result=result+cur.getString(iSNO)+"\t"+cur.getString(iTEXT)+"\t"+cur.getString(iPeople)+"\t"+cur.getString(iScreenshot)+"\t"+cur.getString(iTime)+"`";
        }
        return result;
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Q_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
