package pl.wicher.easiestchecklist.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.wicher.easiestchecklist.model.Item

interface ItemDAO {
    @Insert
    fun addItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM items WHERE checkListId = :id ")
    fun retrieveItemsForChecklist(id: Int) : ArrayList<Item>
}