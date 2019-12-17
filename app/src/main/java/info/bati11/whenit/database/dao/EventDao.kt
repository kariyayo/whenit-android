package info.bati11.whenit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import info.bati11.whenit.database.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM event ORDER BY id DESC LIMIT 1")
    fun selectLatest(): LiveData<EventEntity?>
}
