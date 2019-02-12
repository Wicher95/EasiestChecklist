package pl.wicher.easiestchecklist.Repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.DataBase.DatabaseManager;
import pl.wicher.easiestchecklist.Model.CheckItem;
import pl.wicher.easiestchecklist.Model.Checklist;

public class ChecklistRepo {

    private Checklist checklist;

    public ChecklistRepo(){
        checklist = new Checklist();
    }


    public static String createTable() {
        return "CREATE TABLE " + Checklist.TABLE  + "("
                + Checklist.KEY_ChecklistId  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + Checklist.KEY_Name  + " TEXT,"
                + Checklist.KEY_Icon + " integer);";
    }


    public int insert(Checklist checklist) {
        int checklistId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Checklist.KEY_ChecklistId, checklist.getChecklistId());
        values.put(Checklist.KEY_Name, checklist.getName());
        values.put(Checklist.KEY_Icon, checklist.getIcon());

        // Inserting Row
        checklistId=(int)db.insert(Checklist.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return checklistId;
    }

    public ArrayList<Checklist> getAllLists()
    {
        ArrayList<Checklist> checklists = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + Checklist.TABLE + "." + Checklist.KEY_ChecklistId + ", "
                + Checklist.TABLE + "." + Checklist.KEY_Name + ", "
                + Checklist.TABLE + "." + Checklist.KEY_Icon + " "
                + "FROM " + Checklist.TABLE + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Checklist checklist = new Checklist();
                checklist.setChecklistId(cursor.getString(cursor.getColumnIndex(Checklist.KEY_ChecklistId)));
                checklist.setName(cursor.getString(cursor.getColumnIndex(Checklist.KEY_Name)));
                checklist.setIcon(cursor.getInt(cursor.getColumnIndex(Checklist.KEY_Icon)));

                checklists.add(checklist);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checklists;
    }

    public String getCheckListName(String Id)
    {
        String checklistName = "";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + Checklist.TABLE + "." + Checklist.KEY_ChecklistId + ", "
                + Checklist.TABLE + "." + Checklist.KEY_Name + ", "
                + Checklist.TABLE + "." + Checklist.KEY_Icon + " "
                + "FROM " + Checklist.TABLE + " "
                + "WHERE "+ Checklist.TABLE + "." + Checklist.KEY_ChecklistId + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{Id});
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                checklistName = cursor.getString(cursor.getColumnIndex(Checklist.KEY_Name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checklistName;
    }

    public boolean deleteCheckList(String checklistId)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(CheckItem.TABLE, CheckItem.KEY_ITEM_LIST + "=" + checklistId,null);
        return db.delete(Checklist.TABLE, Checklist.KEY_ChecklistId + "=" + checklistId,null)>0;
    }



    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Checklist.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
