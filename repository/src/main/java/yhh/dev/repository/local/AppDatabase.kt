package yhh.dev.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnimalHospitalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAnimalHospitalDao(): AnimalHospitalDao
}