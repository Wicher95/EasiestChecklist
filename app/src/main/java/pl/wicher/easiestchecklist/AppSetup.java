package pl.wicher.easiestchecklist;

import android.app.Application;
import android.content.Context;

import pl.wicher.easiestchecklist.DataBase.DBHelper;
import pl.wicher.easiestchecklist.DataBase.DatabaseManager;

public class AppSetup extends Application {

    private Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper(context);
        DatabaseManager.initializeInstance(dbHelper);
    }
}
