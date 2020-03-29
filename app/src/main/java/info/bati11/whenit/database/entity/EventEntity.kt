package info.bati11.whenit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val title: String,

    val year: Int,

    // 1-12
    val month: Int,

    // 1-31
    val dayOfMonth: Int,

    val createdAt: Long,

    val updatedAt: Long
)
