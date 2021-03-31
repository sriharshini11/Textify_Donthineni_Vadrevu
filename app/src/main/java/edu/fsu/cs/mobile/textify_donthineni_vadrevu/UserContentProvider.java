package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {
    public static final String AUTHORITY = "edu.fsu.cs.mobile.textify_donthineni_vadrevu.provider";
    public final static String DBNAME = "textify.db";
    public final static String TABLE_USERS = "users";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_PASSWORD = "password";
    public final static String COLUMN_EMAIL = "email";
    public final static String [] columns={COLUMN_NAME,COLUMN_PASSWORD,COLUMN_EMAIL};
    public static final Uri CONTENT_URI = Uri.parse(
            "content://edu.fsu.cs.mobile.textify_donthineni_vadrevu.provider/" + TABLE_USERS);
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_USERS +
            "(" +
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_NAME +
            " TEXT," +
            COLUMN_PASSWORD +
            " TEXT," +
            COLUMN_EMAIL +
            " TEXT)" ;

    private UserContract mOpenHelper;
    @Override
    public boolean onCreate()
    {
        mOpenHelper = new UserContract(getContext());
        return true;
    }
    public Uri insert(Uri uri, ContentValues values)
    {

        String name = values.getAsString(COLUMN_NAME).trim();
        String password = values.getAsString(COLUMN_PASSWORD).trim();
        String email = values.getAsString(COLUMN_EMAIL).trim();

        if (name.equals(""))
            return null;
        if (password.equals(""))
            return null;
        if (email.equals(""))
            return null;

        long user_id = mOpenHelper.getWritableDatabase().insert(TABLE_USERS, null, values);
        return Uri.withAppendedPath(CONTENT_URI, "" + user_id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        return mOpenHelper.getWritableDatabase().update(TABLE_USERS, values, selection, selectionArgs);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return mOpenHelper.getWritableDatabase().delete(TABLE_USERS, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_USERS, projection, selection, selectionArgs,
                null, null, sortOrder);
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }

    protected static final class UserContract extends SQLiteOpenHelper
    {
        private static final int DATABASE_VERSION = 6;
        UserContract(Context context) {
            super(context, DBNAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            System.out.println(SQL_CREATE_MAIN);
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
        {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(arg0);
        }
    }
    public static int getColumnIndex(String column)
    {
        for(int i=0;i<columns.length;i++)
        {
            if(columns[i].equals(column))
            {
                return i+1;
            }
        }
        return -1;
    }



}
