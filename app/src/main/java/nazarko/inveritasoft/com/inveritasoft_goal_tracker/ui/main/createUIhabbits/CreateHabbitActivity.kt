package nazarko.inveritasoft.com.inveritasoft_goal_tracker.ui.main.createUIhabbits

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_create_habbit.*
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.R
import nazarko.inveritasoft.com.inveritasoft_goal_tracker.base.project.BaseActivity

/**
 * Created by nazarko on 23.02.18.
 */
class CreateHabbitActivity:BaseActivity() {

    var mCreatePagerAdapter: CreatePagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habbit)
        mCreatePagerAdapter = CreatePagerAdapter(supportFragmentManager, listOf("Habbit", "Reminders"))
        viewpager.adapter = mCreatePagerAdapter;
        sliding_tabs.setupWithViewPager(viewpager)
        fab.hide()
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int){}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position==1)
                    fab.show()
                else
                    fab.hide()
            }
        })

        val shareViewModel = ViewModelProviders.of(this).get(CreateHabbitShareViewModel::class.java)
        fab.setOnClickListener({
            shareViewModel.clickFab.value = Unit;
        })

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_habits, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cancel -> cancel()
            R.id.menu_save -> save()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface fabClickListener {
        fun OnClickFab()
    }

}