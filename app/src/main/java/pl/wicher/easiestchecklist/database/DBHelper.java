package pl.wicher.easiestchecklist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.wicher.easiestchecklist.model.CheckItem;
import pl.wicher.easiestchecklist.model.Checklist;
import pl.wicher.easiestchecklist.repo.CheckItemRepo;
import pl.wicher.easiestchecklist.repo.ChecklistRepo;


public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 10;
    // Database Name
    private static final String DATABASE_NAME = "simplestChecklist.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(ChecklistRepo.createTable());
        db.execSQL(CheckItemRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Checklist.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CheckItem.TABLE);
        onCreate(db);
    }
}
