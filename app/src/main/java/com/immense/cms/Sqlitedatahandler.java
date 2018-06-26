package com.immense.cms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class Sqlitedatahandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteLoginHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Details";

    // Login table name
    private static final String TABLE_USER = "contacts";

    // Login Table Columns names
    private static final String KEY_COMPANYNAME = "companyname";
    private static final String KEY_SALUTATION  = "salutation";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_PHONENUMBER = "phonenumber";
    private static final String KEY_MOBILENUMBER = "mobilenumber";
    private static final String KEY_EMAILADDRESS = "emailaddress";
    private static final String KEY_WEBSITE = "website";


    String aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,ll;

    public Sqlitedatahandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_COMPANYNAME + " TEXT,"
                + KEY_SALUTATION + " TEXT," + KEY_FULLNAME+ " TEXT,"+ KEY_DESIGNATION + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_CITY + " TEXT," + KEY_STATE + " TEXT,"+ KEY_COUNTRY + " TEXT,"
                + KEY_PHONENUMBER + " TEXT," + KEY_MOBILENUMBER + " TEXT,"
                + KEY_EMAILADDRESS + " TEXT," + KEY_WEBSITE + " TEXT,"+"'')";

        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }


     // Storing conatct details in database

    public void addContactToDB(String companyname, String salutation, String fullname, String designation, String address,
                               String city, String state, String country, String phonenumber,
                               String mobilenumber, String emailaddress, String website) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMPANYNAME, companyname);
        values.put(KEY_SALUTATION, salutation);
        values.put(KEY_FULLNAME, fullname);
        values.put(KEY_DESIGNATION, designation);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_CITY, city);
        values.put(KEY_STATE, state);
        values.put(KEY_COUNTRY, country);
        values.put(KEY_PHONENUMBER, phonenumber);
        values.put(KEY_MOBILENUMBER, mobilenumber);
        values.put(KEY_EMAILADDRESS, emailaddress);
        values.put(KEY_WEBSITE, website);




        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New details inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<String> listContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array = new ArrayList<>();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_USER, null);
        if (c.moveToFirst()) {
            if (c != null) {
                do {
                    for (int i = 0; i <= 11; i++) {
                        array.add(c.getString(i));
                    }
                } while (c.moveToNext() && c != null);
            }
            return array;
        } else {
            return array;
        }
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}

