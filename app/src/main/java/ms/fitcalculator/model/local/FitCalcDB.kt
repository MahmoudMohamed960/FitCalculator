package ms.fitcalculator.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName ="fit_calc_table")
class FitCalcDB (@PrimaryKey val date: String,val value:String)