package pl.wicher.easiestchecklist.Repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pl.wicher.easiestchecklist.DataBase.DatabaseManager;
import pl.wicher.easiestchecklist.Model.CheckItem;
import pl.wicher.easiestchecklist.Model.Checklist;

public class CheckItemRepo {

    private CheckItem checkItem;

    public CheckItemRepo() {
        checkItem = new CheckItem();
    }


    public static String createTable() {
        return "CREATE TABLE " + CheckItem.TABLE + "("
                + CheckItem.KEY_CheckItemId + "   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL    ,"
                + CheckItem.KEY_Title + "   TEXT    ,"
                + CheckItem.KEY_IsChecked + "   integer    ,"
                + CheckItem.KEY_ITEM_LIST + "   TEXT    ,"
                + " FOREIGN KEY (" + CheckItem.KEY_ITEM_LIST + ") REFERENCES " + Checklist.TABLE + " (" + CheckItem.KEY_ChecklistId + "));";
    }


    public int insert(CheckItem checkItem) {
        int checkItemId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(CheckItem.KEY_CheckItemId, checkItem.getCheckItemId());
        values.put(CheckItem.KEY_Title, checkItem.getTitle());
        values.put(CheckItem.KEY_IsChecked, checkItem.isChecked());
        values.put(CheckItem.KEY_ITEM_LIST, checkItem.getCheckListId());

        // Inserting Row
        checkItemId = (int) db.insert(CheckItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return checkItemId;
    }

    public ArrayList<CheckItem> getAllItemsById(String checkListId) {
        ArrayList<CheckItem> checkItems = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + CheckItem.TABLE + "." + CheckItem.KEY_CheckItemId + ", "
                + CheckItem.TABLE + "." + CheckItem.KEY_Title + ", "
                + CheckItem.TABLE + "." + CheckItem.KEY_IsChecked + " "
                + "FROM " + CheckItem.TABLE + " "
                + "WHERE " + CheckItem.TABLE + "." + CheckItem.KEY_ITEM_LIST + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{checkListId});
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CheckItem checkItem = new CheckItem();
                checkItem.setCheckItemId(cursor.getString(cursor.getColumnIndex(CheckItem.KEY_CheckItemId)));
                checkItem.setTitle(cursor.getString(cursor.getColumnIndex(CheckItem.KEY_Title)));
                checkItem.setChecked(cursor.getInt(cursor.getColumnIndex(CheckItem.KEY_IsChecked)));
                checkItems.add(checkItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkItems;
    }

    public int[] countAllItemsById(String checklistId) {
        int counter[] = {0, 0};
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + CheckItem.TABLE + "." + CheckItem.KEY_CheckItemId + ", "
                + CheckItem.TABLE + "." + CheckItem.KEY_IsChecked + " "
                + "FROM " + CheckItem.TABLE + " "
                + "WHERE " + CheckItem.TABLE + "." + CheckItem.KEY_ITEM_LIST + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{checklistId});
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(CheckItem.KEY_IsChecked)) == 1)
                    counter[0]++;
                counter[1]++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return counter;
    }

    public boolean updateItem(String checkItemId, int value)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues args = new ContentValues();
        args.put(CheckItem.KEY_CheckItemId, checkItemId);
        args.put(CheckItem.KEY_IsChecked, value);
        return db.update(CheckItem.TABLE, args, CheckItem.KEY_CheckItemId + "=" + checkItemId,null)>0;
    }

    public boolean deleteCheckItem(String checkItemId)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        return db.delete(CheckItem.TABLE, CheckItem.KEY_CheckItemId + "=" + checkItemId,null)>0;
    }


    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Checklist.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
