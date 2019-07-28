package ms.fitcalculator


import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.bmi_result_fragment.view.*
import ms.fitcalculator.model.local.BmiCalculator
import ms.fitcalculator.model.local.FitCalcDB
import ms.fitcalculator.ui.activities.StoreActivity
import ms.fitcalculator.ui.fragments.ScreenShotFragment
import ms.fitcalculator.view_model.FitViewModel
import java.text.SimpleDateFormat
import java.util.*

class BMIResultFragment() : Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var fitCalcDB: FitCalcDB
    var isItemFound=false
    private lateinit var fitViewModel: FitViewModel
    companion object {
        lateinit var popupWindow: PopupWindow
        fun newInstance(bmiValue: String): Fragment {
            val args = Bundle()
            args.putString("value", bmiValue)
            val fragment = BMIResultFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.bmi_result_fragment, container, false)
        val btnRecalculate: Button? = view?.findViewById(R.id.recalculate)
        val tvType: TextView? = view?.findViewById(R.id.bmi_type)
        val tvTitle: TextView? = view?.findViewById(R.id.bmi_range_title)
        val tvRange: TextView? = view?.findViewById(R.id.bmi_range_value)
        val tvDescription: TextView? = view?.findViewById(R.id.bmi_description)
        mainActivity = activity as MainActivity
        btnRecalculate!!.setOnClickListener { view ->
            mainActivity.navigateFragments(BMIFragment.newInstance())
        }
        var bmiValue = arguments!!.getString("value")
        val tvVlaue: TextView? = view?.findViewById(R.id.bmi_vlaue)
        var bmiCalculator = BmiCalculator()
        bmiValue = bmiCalculator.convertNumber(bmiValue)
        bmiCalculator.getBMIInformaton(bmiValue)
        tvVlaue!!.text = bmiValue
        tvType!!.text = bmiCalculator.type
        tvDescription!!.text = bmiCalculator.description
        tvRange!!.text = bmiCalculator.range
        tvTitle!!.text = bmiCalculator.rangeTitle
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        fitCalcDB= FitCalcDB(currentDate.toString(), bmiValue)
        view.save.setOnClickListener {
            openPopUp(view)
        }
        return view
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun openPopUp(view: View?) {
        val popupView = layoutInflater.inflate(R.layout.save_popup, null)
        popupWindow = PopupWindow(popupView, 480, 300)
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
        val btnClose = popupView.findViewById<Button>(R.id.close_popup)
        btnClose.setOnClickListener {
            popupWindow.dismiss()
        }
        val btnScreen = popupView.findViewById<Button>(R.id.screen_save)
        btnScreen.setOnClickListener {
            popupWindow.dismiss()
            var viewLayout = view!!.findViewById<LinearLayout>(R.id.result_layout)
            val screen = takeScreenFromView(viewLayout, viewLayout.width, viewLayout.height)
            MainActivity.navPosition = 2
            mainActivity.navigateFragments(ScreenShotFragment.newInstance(screen))
        }
        val btnItems = popupView.findViewById<Button>(R.id.item_popup)
        btnItems.setOnClickListener {
            if(isItemFound==false) {
                isItemFound = true
                fitViewModel = ViewModelProviders.of(this).get(FitViewModel::class.java)
                fitViewModel.insert(fitCalcDB)
                var intent = Intent(context, StoreActivity::class.java)
                startActivity(intent)
                popupWindow.dismiss()
            }else
            {
                Toast.makeText(context,context!!.resources.getString(R.string.execpetion_id),Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun takeScreenFromView(v: View, width: Int, height: Int): Bitmap {
        val screen = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screen)
        v.draw(canvas)
        return screen
    }


}