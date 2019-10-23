package yhh.dev.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface AnimalHospitalDao {

    @Query("select * from AnimalHospitalEntity")
    fun getAll(): Single<List<AnimalHospitalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: AnimalHospitalEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<AnimalHospitalEntity>): List<Long>
}