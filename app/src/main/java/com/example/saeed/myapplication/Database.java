package com.example.saeed.myapplication;


import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public final String path = "data/data/com.example.saeed.myapplication/databases/";
    public final String Name = "temp";
    public SQLiteDatabase mydb;
    private final Context mycontext;

    public Database(Context context) {
        super(context, "temp", null, 1);
        mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    public void useable() {
        boolean checkdb = checkdb();
        if (checkdb) {

        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {

            }
        }
    }

    public void open() {
        mydb = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void close() {
        mydb.close();
    }

    public boolean checkdb() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {

        }
        return db != null ? true : false;
    }

    public void copydatabase() throws IOException {

        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int lenght;
        InputStream myInput = mycontext.getAssets().open(Name);
        while ((lenght = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, lenght);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public String namayesh(int row, int field, String table) {

        android.database.Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table, null);
        Cursor.moveToPosition(row);
        String str = Cursor.getString(field);
        return str;
    }

    public Integer shomaresh_field(String table, String field) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + " group by " + field, null);
        int i = Cursor.getCount();
        return i;
    }

    public String namayesh_fasl(String table, int row) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + " group by season", null);
        Cursor.moveToPosition(row);
        String s = Cursor.getString(4);
        return s;
    }

    public Integer shomaresh_dastan(String table, String season) {
        Cursor Cursor = mydb.rawQuery("SELECT * FROM " + table + " where season='" + season + "' group by name", null);
        int s = Cursor.getCount();
        return s;

    }

    public String namayesh_dastan(String table, int row, String season, int field) {
        Cursor Cursor = mydb.rawQuery("select * from " + table + " where season='" + season + "' group by name", null);
        Cursor.moveToPosition(row);
        String save = Cursor.getString(field);
        return save;

    }

    public Integer shomaresh_safhe_dastan(String table, String season, String story) {
        Cursor Cursor = mydb.rawQuery("select * from " + table + " where season='" + season + "' and name='" + story + "'", null);
        int save = Cursor.getCount();
        return save;
    }

    public String namayesh_matn(String table, String season, String name, int page) {
        Cursor Cursor = mydb.rawQuery("select * from " + table + " where season='" + season + "' and name='" + name + "' and page=" + page, null);
        Cursor.moveToFirst();
        String save = Cursor.getString(2);
        return save;
    }
}

