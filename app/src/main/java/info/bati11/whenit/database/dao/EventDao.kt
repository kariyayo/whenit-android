package info.bati11.whenit.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import info.bati11.whenit.database.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM event WHERE id = :id")
    fun findById(id: Long): EventEntity?

    @Insert
    fun insert(entity: EventEntity)

    @Update
    fun update(entity: EventEntity)

    @Query("DELETE FROM event WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM event ORDER BY id DESC LIMIT 1")
    fun selectLatest(): LiveData<EventEntity?>

    @Query(
        """
        SELECT DISTINCT * FROM (
            SELECT * FROM (SELECT * FROM event WHERE month >= :month AND dayOfMonth >= :dayOfMonth ORDER BY month, dayOfMonth) AS a
            UNION ALL
            SELECT * FROM (SELECT * FROM event WHERE month <= :month OR ( month = :month AND dayOfMonth < :dayOfMonth ) ORDER BY month, dayOfMonth) AS b
        )
        """
    )
    fun allEventsOrderByNearly(month: Int, dayOfMonth: Int): DataSource.Factory<Int, EventEntity>
}
