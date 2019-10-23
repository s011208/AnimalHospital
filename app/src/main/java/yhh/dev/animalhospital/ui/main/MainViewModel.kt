package yhh.dev.animalhospital.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import yhh.dev.repository.AnimalHospitalRepository
import yhh.dev.repository.local.AnimalHospitalEntity

class MainViewModel(private val repository: AnimalHospitalRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val animalHospitalList = MutableLiveData<List<AnimalHospitalEntity>>()

    private val loading = MutableLiveData<Boolean>()

    private val errorLabel = MutableLiveData<Boolean>()

    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    init {
        fetch()
    }

    fun getLoading() = loading

    fun getErrorLabel() = errorLabel

    fun getAnimalHospitalList() = animalHospitalList

    fun fetch() {
        compositeDisposable.add(
            repository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loading.value = true
                    errorLabel.value = false
                }
                .subscribe(
                    {
                        Timber.v("size: ${it.size}")
                        loading.value = false
                        animalHospitalList.value = it
                    },
                    {
                        Timber.w(it, "failed to get data")
                        loading.value = false
                        errorLabel.value = true
                    }
                )
        )
    }
}
