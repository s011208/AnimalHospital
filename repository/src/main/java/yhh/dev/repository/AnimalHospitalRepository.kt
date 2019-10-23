package yhh.dev.repository

import android.os.SystemClock
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber
import yhh.dev.repository.local.AnimalHospitalEntity
import yhh.dev.repository.local.AppDatabase

class AnimalHospitalRepository(private val database: AppDatabase) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.coa.gov.tw/Service/OpenData/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(Repository::class.java)

    private interface Repository {
        @GET("http://data.coa.gov.tw/Service/OpenData/DataFileService.aspx?UnitId=078&\$top=99999")
        fun getData(): Single<List<AnimalHospitalEntity>>
    }

    fun get() =
        Single.concat<List<AnimalHospitalEntity>>(
            database.getAnimalHospitalDao().getAll()
                .doOnSuccess {
                    Timber.v("from local, size: ${it.size}")
                },
            retrofit.getData()
                .doOnSuccess {
                    Timber.v("from remote, size: ${it.size}")
                    database.getAnimalHospitalDao().insert(it)
                }
        ).filter {
            it.isNotEmpty() && (it[0].timeStamp >= SystemClock.elapsedRealtime() - 1000 * 60 * 60 * 24 * 20 || it[0].timeStamp == 0L)
        }.firstOrError()
}