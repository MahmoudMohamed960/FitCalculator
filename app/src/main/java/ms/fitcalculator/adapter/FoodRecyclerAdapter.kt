package ms.fitcalculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ms.fitcalculator.R
import ms.fitcalculator.model.remote.FoodNutrients

class FoodRecyclerAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<FoodRecyclerAdapter.FoodRecyclerHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<FoodNutrients.Hint>()
    private lateinit var foodItem: FoodNutrients
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecyclerHolder {
        val itemView = inflater.inflate(R.layout.food_item, parent, false)
        return FoodRecyclerHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FoodRecyclerHolder, position: Int) {
        var item = items[position]
        holder.txtBrand.text = item.food.brand
        holder.txtFoodContent.text = item.food.foodContentsLabel
        holder.txtCalories.text = item.food.nutrients.eNERCKCAL.toString()
    }


    internal fun setItems(foodItem: FoodNutrients) {
        this.foodItem = foodItem
        this.items = foodItem.hints
        notifyDataSetChanged()
    }

    inner class FoodRecyclerHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val txtCalories: TextView = itemView.findViewById(R.id.txt_calories_value)
        val txtBrand: TextView = itemView.findViewById(R.id.txt_brand_value)
        val txtFoodContent: TextView = itemView.findViewById(R.id.txt_food_content_value)
    }

}