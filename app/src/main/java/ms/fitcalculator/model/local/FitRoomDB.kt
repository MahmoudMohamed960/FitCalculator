package ms.fitcalculator.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(FitCalcDB::class), version = 2)
public abstract class FitRoomDB : RoomDatabase(){
    abstract fun fitDao(): FitCalcDAO
    companion object {
        @Volatile
        private var INSTANCE: FitRoomDB? = null

        fun getDatabase(context: Context , scope: CoroutineScope): FitRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitRoomDB::class.java,
                    "Fit_calc_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}