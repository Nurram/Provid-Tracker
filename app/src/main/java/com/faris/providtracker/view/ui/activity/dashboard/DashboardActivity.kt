package com.faris.providtracker.view.ui.activity.dashboard

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.faris.providtracker.R
import com.faris.providtracker.databinding.ActivityDashboardBinding
import com.faris.providtracker.view.ui.fragment.about.AboutFragment
import com.faris.providtracker.view.ui.fragment.home.HomeFragment
import com.faris.providtracker.view.ui.fragment.knowledge.KnowledgeFragment
import com.google.android.material.navigation.NavigationBarView

class DashboardActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private val fragments = listOf(
        KnowledgeFragment.newInstance(),
        HomeFragment.newInstance(),
        AboutFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(fragments[1])
        binding.apply {
            btnavDahboard.selectedItemId = R.id.btnav_home
            btnavDahboard.setOnItemSelectedListener(this@DashboardActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnav_knowledge -> changeFragment(fragments[0])
            R.id.btnav_home -> changeFragment(fragments[1])
            R.id.btnav_about_us -> changeFragment(fragments[2])
        }

        return true
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }
}