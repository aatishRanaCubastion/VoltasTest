package com.cubastion.voltastest.others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cubastion.voltastest.get_set.Users;

import java.util.ArrayList;

/**
 * Created by Aatish Rana on 3/16/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "voltas_db";
    private static final String TABLE_CONTACTS = "user_details";

    private static final String AREA = "Area";
    private static final String CONTACT = "Contact";
    private static final String CONTACTPHONE = "ContactPhone";
    private static final String OWNEDBYID = "OwnedById";
    private static final String SRNUMBER = "SRNumber";
    private static final String VIP = "VIP";
    private static final String SEVERITY = "Severity";
    private static final String STATUS = "Status";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + AREA + " TEXT," + CONTACT + " TEXT,"
                + CONTACTPHONE + " TEXT," + OWNEDBYID + " TEXT,"
                + SRNUMBER + " TEXT," + VIP + " TEXT," + SEVERITY + " TEXT,"
                + STATUS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addUser(Users users_details)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AREA, users_details.getArea());
        values.put(CONTACT, users_details.getContact());
        values.put(CONTACTPHONE, users_details.getContactPhone());
        values.put(OWNEDBYID, users_details.getOwnedById());
        values.put(SRNUMBER, users_details.getSR_No()+"");
        values.put(VIP, users_details.getVIP());
        values.put(SEVERITY, users_details.getSeverity());
        values.put(STATUS, users_details.getStatus());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }
    
    public  boolean addUserList(ArrayList<Users> userlist)
    {
        if(!userlist.isEmpty()) {
            int count = userlist.size();
            for (int i = 0; i < count; i++) {
                Users ud = userlist.get(i);
                addUser(ud);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public ArrayList<Users> getallUsers()
    {
        ArrayList<Users> userList = new ArrayList<Users>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Users users_details = new Users();
                users_details.putArea(cursor.getString(0));
                users_details.putContact(cursor.getString(1));
                users_details.putContactPhone(cursor.getString(2));
                users_details.putOwnerId(cursor.getString(3));
                users_details.putSR_No(Long.parseLong(cursor.getString(4)));
                users_details.putVip(cursor.getString(5));
                users_details.putSeverity(cursor.getString(6));
                users_details.putStatus(cursor.getString(7));
                userList.add(users_details);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return userList;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int i=cursor.getCount();
        cursor.close();
        return i;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CONTACTS);
        db.close();
    }
}
