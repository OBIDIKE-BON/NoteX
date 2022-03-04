package com.stackfloat.notex.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.stackfloat.notex.NoteListFragmentDirections
import com.stackfloat.notex.POSITION_NOT_SET
import com.stackfloat.notex.R

class NoteXActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_x)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawer_layout)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

//        val  navController = findNavController(R.id.nav_host_fragment_container)
        navController = navHostFragment.findNavController()
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            navController.navigate(
                NoteListFragmentDirections.navigateToEditNote(POSITION_NOT_SET)
            )
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_action_notes,
//                R.id.nav_action_todo,
//                R.id.nav_action_shopping
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}