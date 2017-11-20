package com.example.captainhumza.fyp_version3.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Abdul Rehman on 11/4/2017.
 */

public class DatabaeHelper extends SQLiteOpenHelper {


    public static final String TableName ="";
    public static final String UserID ="";
    public static final String PersonID ="";
    public static final String Token ="";
    public  static  final  String DataBaseName ="VirtualMart";


    public static int check =0;

    public DatabaeHelper(Context context) {
        super(context, DataBaseName , null , 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db = this.getWritableDatabase();
        if(check ==0)
        {
            CreateTable(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateTable (SQLiteDatabase db)
    {
        try
        {
            db.execSQL("Create Table"+TableName+"("+ UserID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +PersonID+"NVARCHAR(50) NOT NULL,"
                    +Token+"NVARCHAR(50)NOT NULL)");



        }
        catch (Exception ex)
        {
            //  Toast.makeText(DbPracLayoutActivity.Instance(),"error or in check method "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        check ++;
    }
    public  boolean InsertDate (int _Personid , String _Token)
    {
        boolean result =false;
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PersonID ,_Personid );
            contentValues.put(Token , _Token);
            long check = db.insert(TableName, null, contentValues);
            if (check ==-1)
            {
                result=  true;
            }
            else
            {
                result =  false;
            }



        }
        catch (Exception ex)
        {
            //Toast.makeText(DbPracLayoutActivity.Instance(),"in method"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return  result;

    }
    public Cursor SelectTokenFromTable ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select Token From " +TableName , null);
        return res;
    }
}
