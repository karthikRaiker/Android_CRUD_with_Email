package com.example.emaill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="automobile.db";
    public static final String TABLE_Name="automobile_table";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="COMPANY";
    public static final String COL_4="MODEL";
    public Dbhandler(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_Name+"(ID INTEGER PRIMARY KEY,NAME TEXT,COMPANY TEXT,MODEL INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_Name);
        onCreate(db);

    }
    public boolean insertData(String name,String company,String model){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,company);
        contentValues.put(COL_4,model);
        long result=db.insert(TABLE_Name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getALLDATA(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_Name,null);
        return res;
    }
    public boolean updateData(String id,String name,String company,String model){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,company);
        contentValues.put(COL_4,model);
        db.update(TABLE_Name,contentValues,"ID=?",new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_Name,"ID=?",new String[]{id});
    }
}
