package ms.fitcalculator

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


class CaloriesFagment : Fragment(),SeekBar.OnSeekBarChangeListener {

    var isMaleBtnClicked = true
    var isFemaleBtnClicked = false
    var age = 19
    var weight = 50
    var height = 70
    var result=0.0;
    lateinit var calories: String
    lateinit var seekBar: SeekBar
    lateinit var ageValue: TextView
    lateinit var weightValue: TextView
    lateinit var heigthVlaue: TextView
    lateinit var addAge: ImageButton
    lateinit var addWeight: ImageButton
    lateinit var minusAge: ImageButton
    lateinit var minusWeight: ImageButton
    lateinit var imageMale:ImageButton
    lateinit var imageFemale:ImageButton
    lateinit var buttonMale:LinearLayout
    lateinit var buttonFemale:LinearLayout
    lateinit var popupWindow: PopupWindow

    companion object {
        fun newInstance(): Fragment {
            return CaloriesFagment()
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =  inflater!!.inflate(R.layout.calories_fragment,container,false)
        seekBar = view?.findViewById(R.id.seekBar)
        ageValue = view?.findViewById(R.id.age_value)
        weightValue = view?.findViewById(R.id.weight_value)
        heigthVlaue = view?.findViewById(R.id.heigth_vlaue)
        addAge = view?.findViewById(R.id.img_add_age)
        addWeight = view?.findViewById(R.id.img_add_weight)
        minusAge = view?.findViewById(R.id.img_minus_age)
        minusWeight = view?.findViewById(R.id.img_minus_weight)
        imageFemale=view?.findViewById(R.id.img_female_icon)
        imageMale=view?.findViewById(R.id.img_male_icon)
        buttonMale=view?.findViewById(R.id.btn_male)
        buttonFemale=view?.findViewById(R.id.btn_female)
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
            isFemaleBtnClicked = savedInstanceState.getBoolean("female")
            isMaleBtnClicked = savedInstanceState.getBoolean("male")
            isChecked(isMaleBtnClicked,isFemaleBtnClicked,mainActivity)
        } else {
            isMaleBtnClicked = true
            isFemaleBtnClicked = false
            age = 19
            weight = 50
            height = 70
        }
        //button calcluate onclicked
        val btnCalculate: Button? = view?.findViewById(R.id.btn_cal_calc)
        btnCalculate!!.setOnClickListener { view ->
            if(isMaleBtnClicked)
            {
               result=(13.397*weight)+(4.799*height)-(5.677*age)+88.362
            }
            else
            {
                result=(9.247*weight)+(3.098*height)-(4.330*age)+447.593

            }
            openPopUp(container)
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
        buttonMale!!.setOnClickListener{
            if(isMaleBtnClicked!=true) {
                isMaleBtnClicked = true
                isFemaleBtnClicked = false
                isChecked(isMaleBtnClicked, isFemaleBtnClicked, mainActivity)
            }
        }
        buttonFemale!!.setOnClickListener{
            if(isFemaleBtnClicked!=true) {
                isMaleBtnClicked = false
                isFemaleBtnClicked = true
                isChecked(isMaleBtnClicked, isFemaleBtnClicked, mainActivity)
            }
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

    //check female or male
    fun isChecked(male: Boolean, female: Boolean, mainActivity: MainActivity) {
         if (male == true) {
             DrawableCompat.setTint(imageMale.drawable, ContextCompat.getColor(mainActivity, R.color.white))
             DrawableCompat.setTint(imageFemale.drawable, ContextCompat.getColor(mainActivity, R.color.disableColor))
         }
         if (female == true) {
             DrawableCompat.setTint(imageFemale.drawable, ContextCompat.getColor(mainActivity, R.color.white))
             DrawableCompat.setTint(imageMale.drawable, ContextCompat.getColor(mainActivity, R.color.disableColor))
         }

     }

    private fun openPopUp(view: View?) {
        var factor=1.2;
        val popupView = layoutInflater.inflate(R.layout.calories_popup, null)
        popupWindow = PopupWindow(popupView, 480, 350)
        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(view as ViewGroup?)
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isFocusable
        popupWindow.update()

        val txtResult=popupView.findViewById<TextView>(R.id.calories_need_result)
        txtResult.text=(result*factor).toString()

        val adapter=ArrayAdapter<CharSequence>(activity,R.layout.spinner_text,resources.getStringArray(R.array.physical_state))
        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down)
        val spinner=popupView.findViewById<Spinner>(R.id.calories_popup_spinner)
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==0)
                 factor=1.2;
                else if(position==1)
                 factor=1.375
                else if(position==2)
                 factor=1.55;
                else if(position==3)
                    factor=1.725;
                else if(position==4)
                    factor=1.9;
                txtResult.text="%.2f".format(result*factor)
            }

        }
        val btnClose=popupView.findViewById<Button>(R.id.close_popup)
        btnClose.setOnClickListener {
            popupWindow.dismiss()
        }

    }

    //save instance
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState!!.putString("age", age.toString())
        outState!!.putString("weight", weight.toString())
        outState!!.putString("height", height.toString())
        outState!!.putBoolean("male", isMaleBtnClicked)
        outState!!.putBoolean("female", isFemaleBtnClicked)
    }


}