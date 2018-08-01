package sjsu.tart.duba;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by lion7 on 2018-07-28.
 */

public class DbOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;

    private DatabaseHelper mDBHelper;
    private Context mCtx;
    private String[] cols = {"markerid", "title", "tag", "lan", "lon", "color"};
    private int colsNum = 6;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String markerid, String title, String tag, String lan, String lon, String color){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.MARKERID, markerid);
        values.put(DataBases.CreateDB.TITLE, title);
        values.put(DataBases.CreateDB.TAG, tag);
        values.put(DataBases.CreateDB.LAN, lan);
        values.put(DataBases.CreateDB.LON, lon);
        values.put(DataBases.CreateDB.COLOR, color);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String markerid, String title, String tag, String lan, String lon, String color){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.MARKERID, markerid);
        values.put(DataBases.CreateDB.TITLE, title);
        values.put(DataBases.CreateDB.TAG, tag);
        values.put(DataBases.CreateDB.LAN, lan);
        values.put(DataBases.CreateDB.LON, lon);
        values.put(DataBases.CreateDB.COLOR, color);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }
    // Select DB
    public Cursor selectColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public String[] selectColumn(String col, String compare){
        String[] ret = new String[50];
        Cursor iCursor = this.selectColumns();
        int numOfResult = 0;

        while(iCursor.moveToNext()){
            String temp = iCursor.getString(iCursor.getColumnIndex(col));
            if(temp.equals(compare)){
                String[] item = new String[6];

                for(int i = 0; i < colsNum; i++){
                    item[i] = iCursor.getString(iCursor.getColumnIndex(cols[i]));
                    ret[numOfResult] += ( item[i] + ",");
                }
                numOfResult++;

            }
        }

        ret[0] = Integer.toString(numOfResult);
        return ret;
    }

    public MarkerData[] getMarkerData(String[] userSelectedTag){
        MarkerData[] markers = new MarkerData[100];
        Cursor iCursor = this.selectColumns();
        int numOfResult = 0;

        while(iCursor.moveToNext()){
            String[] item = new String[6];
            for(int i = 1; i <= colsNum; i++){
                item[i-1] = iCursor.getString(iCursor.getColumnIndex(cols[i-1]));
            }
            markers[numOfResult] =
                    new MarkerData(item[1], item[2], item[3], item[4], item[5]);
            markers[numOfResult++].reviseTags(userSelectedTag);
        }

        MarkerData[] ret = Arrays.copyOf(markers, numOfResult);
        Arrays.sort(ret);

        for(int i = 0; i < numOfResult; i++){
            ret[i].showInfoByLog("sort");
        }

        return ret;
    }

    // sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM usertable ORDER BY " + sort + ";", null);
        return c;
    }

    public void showDatabaseByLog(String sort){
        Cursor iCursor = sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());

        String ret = "";
        int numOfResult = 0;

        while(iCursor.moveToNext()){

            String[] item = new String[6];
            for(int i = 0; i < colsNum; i++){
                item[i] = iCursor.getString(iCursor.getColumnIndex(cols[i]));
                ret += ( setTextLength(item[i], 14) + ",");
            }
            ret += "\n";

        }
        Log.d("showDatabaseByLog", ret);

    }

    private String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

}
