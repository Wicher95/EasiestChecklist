package pl.wicher.easiestchecklist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.wicher.easiestchecklist.model.CheckList
import pl.wicher.easiestchecklist.model.Item

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "local-db"

@Database(version = DATABASE_SCHEMA_VERSION, entities = [CheckList::class, Item::class])
abstract class RoomDatabaseClient : RoomDatabase() {

    abstract fun checklistDAO(): ChecklistDAO
    abstract fun itemDAO(): ItemDAO

    companion object {
        private var instance: RoomDatabaseClient? = null

        fun getInstance(context: Context) : RoomDatabaseClient {
            if(instance == null) {
                instance = createDatabase(context)
            }
            return instance!!
        }


        //TODO move away from main thread queries
        private fun createDatabase(context: Context) : RoomDatabaseClient {
            return Room.databaseBuilder(context, RoomDatabaseClient::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
        }
    }
}