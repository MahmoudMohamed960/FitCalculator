package ms.fitcalculator.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_nutirion.*
import ms.fitcalculator.R
import ms.fitcalculator.adapter.FoodRecyclerAdapter
import ms.fitcalculator.model.local.remote.GETData
import ms.fitcalculator.model.remote.FoodNutrients
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Nutrients : AppCompatActivity() {
    public final var _ID: String = "c0e79deb"
    public final var _KEY: String = "61044ba9c0646ca3a0bcda9158aacdab"
    private var mCompositeDisposable: CompositeDisposable? = null
    private val BASE_URL = "https://api.edamam.com/api/food-database/"
    var adapter: FoodRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutirion)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.food)
        }
        mCompositeDisposable = CompositeDisposable()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        var seacrhItem = menu!!.findItem(R.id.search)
        val searchView = seacrhItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    loadData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData(query: String) {
        val requestInterface = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GETData::class.java)

        mCompositeDisposable?.add(
            requestInterface.getData(query, _ID, _KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(foodItems: FoodNutrients) {
        if (foodItems.parsed.size > 0) {
            no_items.visibility = View.GONE
            view.visibility = View.VISIBLE
            text_Show_layout.visibility= View.VISIBLE
            text_result_layout.visibility= View.VISIBLE
            Picasso.get().load(foodItems.parsed[0].food.image).into(food_image)
            txt_fat_value.text = foodItems.parsed[0].food.nutrients.fAT.toString()
            txt_protein_value.text = foodItems.parsed[0].food.nutrients.pROCNT.toString()
            txt_calories_value.text = foodItems.parsed[0].food.nutrients.eNERCKCAL.toString()
            txt_category_value.text=foodItems.parsed[0].food.category
            txt_vitamin_b12_value.text=foodItems.parsed[0].food.nutrients.fIBTG.toString()
        } else {
            view.visibility= View.GONE
            text_Show_layout.visibility= View.GONE
            text_result_layout.visibility= View.GONE
            no_items.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }
}
