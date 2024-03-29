package com.byjus.assignment.byjusAssignment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.byjus.assignment.byjus_assignment.R
import com.byjus.assignment.byjusAssignment.fragment.NewsFeedFragment
import com.byjus.assignment.byjusAssignment.fragment.NewsViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showNewsScreen()
    }

    private fun showNewsScreen() {
        supportFragmentManager.commit {
            add(
                R.id.fragment_container,
                NewsFeedFragment()
            )
        }
    }

    private fun showNewsViewScreen(bundle: Bundle) {
        supportFragmentManager.commit {
            add(
                R.id.fragment_container,
                NewsViewFragment.newInstance(bundle)
            )
        }
    }

}