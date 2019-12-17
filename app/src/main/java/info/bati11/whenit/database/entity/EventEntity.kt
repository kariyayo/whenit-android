package info.bati11.whenit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String,

    val yyyy: Int,

    val mmdd: Int,

    val createdAt: Long
)
