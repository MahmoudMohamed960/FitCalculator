package ms.fitcalculator.model.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class FitCalcRepo(private val fitDao: FitCalcDAO) {
    val allItems: LiveData<List<FitCalcDB>> = fitDao.getAllitems()
    @WorkerThread
    suspend fun insert(fitCalc: FitCalcDB) {
        fitDao.insert(fitCalc)
    }
    @WorkerThread
     fun delete(fitCalc: FitCalcDB) {
        fitDao.deleteItem(fitCalc)
    }
}