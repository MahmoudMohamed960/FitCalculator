package ms.fitcalculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBar
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*
import ms.fitcalculator.help.LocalManagerSetting
import ms.fitcalculator.ui.activities.Nutrients
import ms.fitcalculator.ui.activities.StoreActivity

class MainActivity() : AppCompatActivity() {
    var fragment = Fragment()

    companion object {
        var bmi = ""
        var navPosition: Int = 0
        var toolbarTitle: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.navigation_icon)
            toolbarTitle=R.string.nav_bmi
            setTitle(toolbarTitle)
        }
        if (savedInstanceState == null) {
            navPosition = R.id.nav_bmi
            navigateFragments(BMIFragment.newInstance())
        } else {
            navPosition = savedInstanceState!!.getInt("position")
            toolbarTitle = savedInstanceState!!.getInt("title")
            toolbar.setTitle(toolbarTitle)
        }
        //set current language
        setDrawerActions()
        val header: View = navigationView.getHeaderView(0)
        val switchLanguage: Switch? = header?.findViewById(R.id.change_language)
        switchLanguage?.setOnCheckedChangeListener { _, isChecked ->
            var currentLanguage: String? = LocalManagerSetting.getCurrentLanguage(this)
            if (currentLanguage!!.equals("en"))
                LocalManagerSetting.setLocal(this, "ar")
            else
                LocalManagerSetting.setLocal(this, "en")
            var intent = intent
            finish()
            startActivity(intent)
        }
    }

    private fun setDrawerActions() {
        navigationView.setCheckedItem(navPosition)
        navigationView.setNavigationItemSelectedListener { drawer_menu ->
            when (drawer_menu.itemId) {
                R.id.nav_bmi -> {
                    if (navPosition != drawer_menu.itemId) {
                        navPosition = R.id.nav_bmi
                        toolbarTitle = R.string.nav_bmi
                        toolbar.setTitle(toolbarTitle)
                        navigateFragments(BMIFragment.newInstance())
                    }
                }
                R.id.nav_calories -> {
                    if (navPosition != drawer_menu.itemId) {
                        toolbarTitle = R.string.nav_calories
                        toolbar.setTitle(toolbarTitle)
                        navPosition = R.id.nav_calories
                        navigateFragments(CaloriesFagment.newInstance())
                    }
                }
                R.id.food_nutirion -> {
                    var goNutrientsActivity = Intent(this, Nutrients::class.java)
                    startActivity(goNutrientsActivity)
                }
                R.id.saved_items -> {
                    var goStoreActivity = Intent(this, StoreActivity::class.java)
                    startActivity(goStoreActivity)
                }

            }
            // set item as selected to persist highlight
            drawer_menu.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()
            true
        }

    }

    fun navigateFragments(fragment: Fragment) {
        this.fragment = fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //appbar - toolbar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT)
        }

        if (fragment != null && navPosition == 1 || navPosition == 2) {
            supportFragmentManager.beginTransaction().remove(fragment).commit();
            supportFragmentManager.popBackStack();
            navPosition = R.id.nav_bmi
        } else {
            finish()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt("position", navPosition)
        outState!!.putInt("title", toolbarTitle)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManagerSetting.onAttach(newBase))
    }
}
