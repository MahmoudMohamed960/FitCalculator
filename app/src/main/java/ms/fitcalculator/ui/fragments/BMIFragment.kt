package ms.fitcalculator

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ms.fitcalculator.model.local.BmiCalculator

class BMIFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    var age = 19
    var weight = 50
    var height = 70
    lateinit var bmi: String
    lateinit var seekBar: SeekBar
    lateinit var ageValue: TextView
    lateinit var weightValue: TextView
    lateinit var heigthVlaue: TextView
    lateinit var addAge: ImageButton
    lateinit var addWeight: ImageButton
    lateinit var minusAge: ImageButton
    lateinit var minusWeight: ImageButton


    companion object {
        fun newInstance(): Fragment {
            return BMIFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.bmi_fragment, container, false)
        seekBar = view?.findViewById(R.id.seekBar)
        ageValue = view?.findViewById(R.id.age_value)
        weightValue = view?.findViewById(R.id.weight_value)
        heigthVlaue = view?.findViewById(R.id.heigth_vlaue)
        addAge = view?.findViewById(R.id.img_add_age)
        addWeight = view?.findViewById(R.id.img_add_weight)
        minusAge = view?.findViewById(R.id.img_minus_age)
        minusWeight = view?.findViewById(R.id.img_minus_weight)
        val mainActivity: MainActivity = activity as MainActivity
        //set seek bar
        seekBar!!.setOnSeekBarChangeListener(this)
        //saved instance
        if (savedInstanceState != null) {
            weight = savedInstanceState!!.getString("weight").toInt()
            weightValue.text = weight.toString()
            height = savedInstanceState!!.getString("height").toInt()
            heigthVlaue!!.text = height.toString()
            seekBar.progress = height
            age = savedInstanceState!!.getString("age").toInt()
            ageValue!!.text = age.toString()
        } else {
            age = 19
            weight = 50
            height = 70

        }
        //button calcluate onclicked
        val btnCalculate: Button? = view?.findViewById(R.id.btn_bmi_calc)
        btnCalculate!!.setOnClickListener { view ->
            var bmiCalculator = BmiCalculator()
            BmiCalculator.context = context
            bmiCalculator.height = height.toDouble()
            bmiCalculator.weight = weight
            bmi = bmiCalculator.calculateBmi()
            MainActivity.bmi = bmi
            MainActivity.navPosition = 1
            mainActivity.navigateFragments(BMIResultFragment.newInstance(bmi))
        }
        //age
        addAge!!.setOnClickListener { view ->
            if (age < 80)
                age = age.inc()
            ageValue!!.text = age.toString()

        }
        minusAge!!.setOnClickListener { view ->
            if (age > 2)
                age = age.dec()
            ageValue!!.text = age.toString()

        }
        //weight
        addWeight!!.setOnClickListener { view ->
            if (weight < 200)
                weight = weight.inc()
            weightValue!!.text = weight.toString()

        }
        minusWeight!!.setOnClickListener { view ->
            if (weight > 5)
                weight = weight.dec()
            weightValue!!.text = weight.toString()

        }

        return view
    }

    //on seekbar changed
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        var min=70
        if(progress<70) {
            seekBar!!.progress = min
            height = 70
            heigthVlaue!!.text = height.toString()
        }
        else {
            height = progress
            heigthVlaue!!.text = progress.toString()
        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    //save instance
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState!!.putString("age", age.toString())
        outState!!.putString("weight", weight.toString())
        outState!!.putString("height", height.toString())

    }


}