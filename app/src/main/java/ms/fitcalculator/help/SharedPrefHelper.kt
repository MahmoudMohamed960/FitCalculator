package ms.fitcalculator.help

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(val context: Context?) {
    private val prefName = "FitCalculator"
    val sharedPref: SharedPreferences = context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPref.edit()

    fun save(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getValueString(key: String): String {
        return sharedPref.getString(key, null)
    }

}