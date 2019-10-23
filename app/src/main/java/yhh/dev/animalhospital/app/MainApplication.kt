package yhh.dev.animalhospital.app

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import yhh.dev.animalhospital.lib.firebase.FirebaseDatabaseHelper
import yhh.dev.animalhospital.ui.hospital.HospitalViewModel
import yhh.dev.animalhospital.ui.main.MainViewModel
import yhh.dev.repository.AnimalHospitalRepository
import yhh.dev.repository.local.AnimalHospitalEntity
import yhh.dev.repository.local.AppDatabase

class MainApplication : Application() {

    val appModule = module {
        single {
            Room.databaseBuilder(this@MainApplication, AppDatabase::class.java, "app.db").build()
        }
        single { AnimalHospitalRepository(get()) }

        single { FirebaseDatabaseHelper() }

        viewModel { MainViewModel(get()) }

        viewModel { (entity: AnimalHospitalEntity) -> HospitalViewModel(get(), entity) }
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}