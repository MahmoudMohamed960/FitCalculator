package ms.fitcalculator.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ms.fitcalculator.R
import ms.fitcalculator.adapter.FitRecyclerAdapter
import ms.fitcalculator.adapter.OnDeleteItemListener
import ms.fitcalculator.help.LocalManagerSetting
import ms.fitcalculator.model.local.FitCalcDB
import ms.fitcalculator.view_model.FitViewModel

class StoreActivity : AppCompatActivity() {
    private lateinit var fitViewModel: FitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.saved)
        }
        val txt_no_items = findViewById<TextView>(R.id.no_items)
        val recyclerView = findViewById<RecyclerView>(R.id.fit_recycler_view)
        val adapter = FitRecyclerAdapter(this,object :OnDeleteItemListener
        {
            override fun onClickedDelete(fitCalcDB: FitCalcDB) {
               createDialog(fitCalcDB)
            }
        })
        fitViewModel = ViewModelProviders.of(this).get(FitViewModel::class.java)
        fitViewModel.allItems.observe(this, Observer { items ->
            items?.let {
                adapter.setItems(items)
                if(items.size==0) {
                    txt_no_items.visibility = View.VISIBLE
                }
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun createDialog(fitCalcDB: FitCalcDB) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(this.resources.getString(R.string.alert_delete_title))
        builder.setMessage(this.resources.getString(R.string.alert_delete_messege))
        builder.setPositiveButton(this.resources.getString(R.string.yes))
        { dialog, which ->
            fitViewModel.delete(fitCalcDB)
        }
        builder.setNegativeButton(this.resources.getString(R.string.no))
        { dialog, which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManagerSetting.onAttach(newBase))
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



}
