package ms.fitcalculator.model.local

import android.content.Context
import ms.fitcalculator.R
import java.text.NumberFormat
import java.util.*

class BmiCalculator {
    lateinit var type: String
    lateinit var rangeTitle: String
    lateinit var range: String
    lateinit var description: String
    var height: Double = 0.0
        get() = field // backing filed to prevent recursive
        set(value) {
            field = value / 100 //convert from cm to m
        }

    var weight: Int = 0
        get() = field // backing filed to prevent recursive
        set(value) {
            field = value
        }

    companion object {
        var context: Context? = null
    }

    fun calculateBmi(): String {
        return "%.2f".format(weight.toDouble() / (height * height))
    }

    fun getBMIInformaton(bmiValue: String) {
        if (bmiValue.toDouble() < 18.5) {
            type = context!!.getString(R.string.underweight_type)
            rangeTitle = context!!.getString(R.string.underweight_range_title)
            range = context!!.getString(R.string.underweight_range)
            description = context!!.getString(R.string.underweight_description)
        }
        if (bmiValue.toDouble() >= 18.5 && bmiValue.toDouble() < 24.5) {
            type = context!!.getString(R.string.normal_type)
            rangeTitle = context!!.getString(R.string.normal_range_title)
            range = context!!.getString(R.string.normal_range)
            description = context!!.getString(R.string.normal_description)
        }
        if (bmiValue.toDouble() >= 25 && bmiValue.toDouble() < 29.9) {
            type = context!!.getString(R.string.overweight_type)
            rangeTitle = context!!.getString(R.string.overweight_range_title)
            range = context!!.getString(R.string.overweight_range)
            description = context!!.getString(R.string.overweight_description)
        }
        if (bmiValue.toDouble() >= 30) {
            type = context!!.getString(R.string.obese_type)
            rangeTitle = context!!.getString(R.string.obese_range_title)
            range = context!!.getString(R.string.obese_range)
            description = context!!.getString(R.string.obese_description)
        }

    }

    fun convertNumber(bmiValue: String): String {
      val chars=bmiValue.toMutableList()
      var result:String=bmiValue.replace("٫",".",ignoreCase = true)
      var format=NumberFormat.getInstance(Locale.US)
      return format.parse(result).toString()
    }

}