package ms.fitcalculator.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface FitCalcDAO {
    @Query("SELECT * from fit_calc_table ORDER BY date ASC")
    fun getAllitems(): LiveData<List<FitCalcDB>>

    @Insert
    suspend fun insert(item: FitCalcDB)

    @Delete
    fun deleteItem (item: FitCalcDB)
}