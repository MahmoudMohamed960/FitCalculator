package ms.fitcalculator.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ms.fitcalculator.model.local.FitCalcDB
import ms.fitcalculator.model.local.FitCalcRepo
import ms.fitcalculator.model.local.FitRoomDB

class FitViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: FitCalcRepo
    val allItems: LiveData<List<FitCalcDB>>

    init {
        val fitDao = FitRoomDB.getDatabase(application,viewModelScope).fitDao()
        repository = FitCalcRepo(fitDao)
        allItems = repository.allItems
    }

    fun insert(item: FitCalcDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun delete(item: FitCalcDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }
}