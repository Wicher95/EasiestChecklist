package pl.wicher.easiestchecklist.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.wicher.easiestchecklist.model.Checklist

interface ChecklistDAO {
    @Insert
    fun addChecklist(checklist: Checklist)

    @Update
    fun updateChecklist(checklist: Checklist)

    @Delete
    fun deleteChecklist(checklist: Checklist)

    @Query("SELECT * FROM checklists")
    fun retrieveChecklists() : MutableList<Checklist>
}