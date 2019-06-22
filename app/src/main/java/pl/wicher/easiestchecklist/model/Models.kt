package pl.wicher.easiestchecklist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "checklists")
data class CheckList(
        @PrimaryKey(autoGenerate = true)
        var uid: Int,
        @ColumnInfo
        var name: String? = null,
        @ColumnInfo
        var icon: Int = 0
)

@Entity(tableName = "items")
data class Item(
        @PrimaryKey(autoGenerate = true)
        var uid: Int,
        @ColumnInfo
        var title: String? = null,
        @ColumnInfo
        var isChecked: Boolean = false,
        @ForeignKey(
                entity = CheckList::class,
                parentColumns = ["uid"],
                childColumns = ["checkListId"],
                onDelete = CASCADE
        )
        var checkListId: Int = 0
)