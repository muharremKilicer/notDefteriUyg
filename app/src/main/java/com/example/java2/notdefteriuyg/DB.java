package com.example.java2.notdefteriuyg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    private static String DBName = "notlarim";
    private static int versiyon = 1;

    public DB(Context context) {
        super(context, DBName + ".db", null, versiyon);
    }

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name + ".db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `notlars` (\n" +
                "\t`notid`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`baslik`\tTEXT,\n" +
                "\t`icerik`\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists notlars");
        onCreate(db);
    }

    public SQLiteDatabase oku() {
        return this.getReadableDatabase();
    }

    public SQLiteDatabase yaz() {
        return this.getWritableDatabase();
    }

    //Ekleme fnc
    public int kayit(String tableName, String[] data) {
        int sonuc = -1;
        String sorgu = "insert into " + tableName + " values(null";
        for (int i = 0; i < data.length; i++) {
            sorgu += " ,'" + data[i] + "'";
        }
        sorgu += ")";

        try {
            yaz().execSQL(sorgu);
            sonuc = 1;
        } catch (Exception ex) {
            sonuc = -1;
        }
        return sonuc;
    }

    public int sil(String tablename, String sutun, String nereID) {
        int a = yaz().delete(tablename, sutun + " = ?", new String[]{nereID});

        return a;
    }

    public void tabloBosalt(String tablo){
        yaz().execSQL("delete from "+ tablo);
    }

    public Cursor dataGetir(String tableName, String query) {
        return oku().rawQuery("select * from " + tableName + " " + query, null);
    }

    public int guncelle(String tablename, String sutunID, String nereID, String[] sutun, String[] satir) {
        ContentValues con = new ContentValues();
        for (int i = 0; i < sutun.length; i++) {
            con.put(sutun[i], satir[i]);
        }

        int a = yaz().update(tablename, con, sutunID + " = ?", new String[]{nereID});

        return a;
    }

}
