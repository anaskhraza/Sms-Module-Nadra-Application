package com.example.anaskhurshid.sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamransharif on 6/5/15.
 */
public class LocationDatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Portal";
    public static final String PORTAL_TABLE_NAME = "NamePortal";
    public static final String PORTAL_COLUMN_ID = "id";
    public static final String PORTAL_COLUMN_FIRSTNAME = "FirstName";
    public static final String PORTAL_COLUMN_LASTNAME = "LastName";
    public static final String PORTAL_COLUMN_VERIFY = "Verify";

    private static String DB_PATH = "/data/data/com.example.anaskhurshid.sms/databases/";

    private static String DB_NAME = "Portal.sqlite";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    private String countryName="";

    private String TAG="";
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public LocationDatabaseHandler(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        try {
            createDataBase();
        }catch (IOException ex){

        }

    }
    public void setTag(String TAG) {

        this.TAG=TAG;

    }
    public void setCountryName(String countryName) {

        this.countryName=countryName;

    }
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {
        //try {
        Log.d("test","db Created");

        boolean dbExist = checkDataBase();

            if(dbExist){
                //do nothing - database already exist
                openDataBase();



                Log.d("test", "Inserting DB");



            }else {



                //By calling this method and empty database will be created into the default system path
                //of your application so we are gonna be able to overwrite that database with our database.
                myDataBase = this.getReadableDatabase();

                myDataBase.execSQL(
                        "create table NamePortal " +
                                "(id integer primary key autoincrement , FirstName text,LastName text,Verify integer DEFAULT false)"
                );
                Log.d("test", "Create Table");
                myDataBase.execSQL(
                        "INSERT INTO NamePortal (FirstName, LastName) VALUES ('انس', 'رضا');");
                myDataBase.execSQL(
                        "INSERT INTO NamePortal (FirstName, LastName) VALUES ('صفا','رضا');");



            }
       // }
      //  catch (IOException e){

        //}

    }
    public Cursor getData(String FName, String LName) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result;
       String FName_edditted = FName.replaceAll("\\s+","");

       String LName_edditted = LName.replaceAll("\\s+","");

        Log.d("Test", "select * from NamePortal where FirstName=" + "'" + FName_edditted + "'" + " AND  LastName=" + "'" + LName_edditted + "'");
        result = myDataBase.rawQuery("select * from NamePortal where FirstName=" +"'" + FName_edditted +"'" +" AND  LastName=" +"'" + LName_edditted+"'", null);

        //int id[] = new int[result.getCount()];

        return result;
    }
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){

            //database does't exist yet.
            e.printStackTrace();

        }catch(Exception e){

            //database does't exist yet.
            e.printStackTrace();
        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    public boolean updateVerify(int verify, int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE NamePortal SET Verify=" + "'" + verify + "'" + "WHERE id=" +"'" +ID +"'");
        /*ContentValues contentValues = new ContentValues();
        Log.d("Test","Inserting values");
        contentValues.put("Verify", verify);
        db.update("NamePortal", contentValues,"Verify",null);*/


        return true;
    }

    public void openDataBase() throws SQLiteException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.







}

