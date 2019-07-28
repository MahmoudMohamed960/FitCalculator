package ms.fitcalculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ms.fitcalculator.R
import ms.fitcalculator.help.LocalManagerSetting
import ms.fitcalculator.model.local.BmiCalculator
import ms.fitcalculator.model.local.FitCalcDB
import java.text.SimpleDateFormat
import java.util.*


class FitRecyclerAdapter internal constructor(context: Context,val deleteItemListener: OnDeleteItemListener) :
    RecyclerView.Adapter<FitRecyclerAdapter.FitRecyclerHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<FitCalcDB>()
    var context = context
    companion object {
        var clickListener:OnDeleteItemListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitRecyclerHolder {
        val itemView = inflater.inflate(R.layout.store_item, parent, false)
        return FitRecyclerHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FitRecyclerHolder, position: Int) {
        clickListener=deleteItemListener
        val current = items[position]
        var bmiCalculator = BmiCalculator()
        var value = current.value
        BmiCalculator.context = context
        bmiCalculator.getBMIInformaton(value)
        holder.txtResult.text = bmiCalculator.type + " ( " + current.value + " )"
        holder.txtDescribtion.text = bmiCalculator.description
        var currentLanguage: String? = LocalManagerSetting.getCurrentLanguage(context)
        if (currentLanguage!!.equals("ar")) {
            var locale = Locale("ar")
            val inputFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", locale)
            var inputDate = inputFormat.parse(current.date)
            var date = inputFormat.format(inputDate)
            holder.txtDate.text = date
        } else {
            var locale = Locale("en")
            val inputFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss", locale)
            var inputDate = inputFormat.parse(current.date)
            var date = inputFormat.format(inputDate)
            holder.txtDate.text = date
        }
        holder.imageDelete.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(v: View?) {
                if (clickListener != null)
                    clickListener?.onClickedDelete(current)
            }

        })

    }

    internal fun setItems(items: List<FitCalcDB>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class FitRecyclerHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val txtResult: TextView = itemView.findViewById(R.id.txt_result)
        val txtDescribtion: TextView = itemView.findViewById(R.id.txt_desc)
        val txtDate: TextView = itemView.findViewById(R.id.txt_date)
        val imageDelete: ImageView = itemview.findViewById(R.id.img_delete)


    }
}