package info.bati11.whenit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.bati11.whenit.database.entity.EventEntity

@Dao
interface EventDao {

    @Insert
    fun insert(entity: EventEntity)

    @Query("DELETE FROM event WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM event ORDER BY id DESC LIMIT 1")
    fun selectLatest(): LiveData<EventEntity?>

    @Query("""
        SELECT * FROM (
            SELECT * FROM (SELECT * FROM event WHERE month >= :month AND dayOfMonth >= :dayOfMonth ORDER BY month, dayOfMonth LIMIT :limit) AS a
            UNION ALL
            SELECT * FROM (SELECT * FROM event WHERE month <= :month OR ( month = :month AND dayOfMonth < :dayOfMonth ) ORDER BY month, dayOfMonth LIMIT :limit) AS b
        ) LIMIT :limit
        """)
    fun selectOrderByNearly(month: Int, dayOfMonth: Int, limit: Int): List<EventEntity>
}
